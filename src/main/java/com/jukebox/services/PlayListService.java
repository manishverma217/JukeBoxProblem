package com.jukebox.services;

import com.jukebox.dtos.PlayListDto;
import com.jukebox.dtos.PlayingSongDto;
import com.jukebox.entities.PlayList;
import com.jukebox.entities.Song;
import com.jukebox.entities.SongStatus;
import com.jukebox.exceptions.*;
import com.jukebox.repositories.IPlayListRepository;
import com.jukebox.repositories.ISongsRepository;
import com.jukebox.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;

public class PlayListService implements IPlayListService{
    private final IPlayListRepository playListRepository;
    private final IUserRepository userRepository;
    private final ISongsRepository songsRepository;

    public PlayListService(IPlayListRepository playListRepository , IUserRepository userRepository , ISongsRepository songsRepository){
        this.playListRepository = playListRepository;
        this.userRepository = userRepository;
        this.songsRepository = songsRepository;
    }

    @Override
    public PlayList createPlayList(String userId, String playListName, List<String> playListSongs) throws UserNotFoundException, SongNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("UserID " + userId + " not found"));
        for(String songId : playListSongs){
            if(songsRepository.existsById(songId)){
                //Song present in Song Repository
            } else {
                throw new SongNotFoundException("SongID" + songId + " not found in Song Repository");
            }
        }
        PlayList playList = new PlayList(playListName, playListSongs, userId);
        playList = playListRepository.save(playList);
        return playList;
    }

    @Override
    public PlayList deletePlayList(String userId, String playListId) throws UserNotFoundException, PlayListNotFoundException, PlayListOnUserNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("UserID " + userId + " not found"));
        PlayList playList = playListRepository.findById(playListId).orElseThrow(() -> new PlayListNotFoundException("PlayListID " + playListId + " not found"));
        if(playListRepository.findBy_PlayListID_UserID(playListId, userId) == null){
            throw new PlayListOnUserNotFoundException("PlayListID " + playListId + " not found on UserID " + userId);
        }
        playListRepository.delete(playList);
        System.out.println("Delete Successful");
        return playList;
    }

    @Override
    public PlayListDto modifyPlayList(SongStatus status, String userId, String playListId,
                                      List<String> playListSongs) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("UserID " + userId + " not found"));
        PlayList playList = playListRepository.findById(playListId).orElseThrow(() -> new PlayListNotFoundException("PlayListID " + playListId + " not found"));
        for(String song : playListSongs){
            if(!songsRepository.existsById(song)){
                throw new SongNotFoundException("SongID " + song + " not found");
            }
        }
        List<String> existingPlayListSongs = playList.getPlayListSongs();
        if(status == SongStatus.ADD){

            boolean isAdded = false;
            for(String newSong : playListSongs){
                if(!existingPlayListSongs.contains(newSong)){
                    existingPlayListSongs.add(newSong);
                    isAdded = true;
                }
            }
            if(!isAdded)
                return new PlayListDto(playList.getPlayListId(), playList.getPlayListName(), playList.getPlayListSongs());

            updatePlayList(playList, null);
            playList.setPlayListSongs(existingPlayListSongs);
            updatePlayList(null, playList);

        } else if(status == SongStatus.DELETE){

            boolean isDeleted = false;
            for(String deleteSong : playListSongs){
                if(existingPlayListSongs.contains(deleteSong)){
                    existingPlayListSongs.remove(deleteSong);
                    isDeleted = true;
                }
            }
            if(!isDeleted)
                return new PlayListDto(playList.getPlayListId(), playList.getPlayListName(), playList.getPlayListSongs());

            updatePlayList(playList, null);
            playList.setPlayListSongs(existingPlayListSongs);
            updatePlayList(null, playList);

        }
        return new PlayListDto(playList.getPlayListId(), playList.getPlayListName(), playList.getPlayListSongs());
    }

    @Override
    public PlayingSongDto playPlayListSong(String userId, String playListId) {
        PlayList playList = playListRepository.findBy_PlayListID_UserID(playListId, userId);
        if(playList == null){
            throw new PlayListOnUserNotFoundException("PlayList ID " + playListId + " not found on " + userId);
        }
        List<String> playListSongs = playList.getPlayListSongs();
        if(playListSongs.isEmpty()){
            throw new PlayListSongsNotFoundException("No songs present in " + playList.getPlayListName());
        }
        String songId = playListSongs.get(0);
        Optional<Song> oSong = songsRepository.findById(songId);
        Song song = oSong.get();
        playListRepository.setCurrentPlayListOnUserID(userId, playListId, songId);
        return new PlayingSongDto(song.getSongName(), song.getAlbum() , song.getFeaturedArtists());
    }

    @Override
    public PlayList updatePlayList(PlayList oldPlayList, PlayList newPlayList) {
        //Old PlayList will be removed from PlayList repository
        //New PlayList will be added to PlayList repository
        PlayList curr = null;
        if(oldPlayList != null){
            playListRepository.delete(oldPlayList);
            curr = oldPlayList;
        }
        if(newPlayList != null){
            playListRepository.save(newPlayList);
            curr = newPlayList;
        }
        return curr;
    }

}

