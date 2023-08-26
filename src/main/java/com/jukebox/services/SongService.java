package com.jukebox.services;

import com.jukebox.dtos.PlayingSongDto;
import com.jukebox.entities.PlayList;
import com.jukebox.entities.Song;
import com.jukebox.entities.SongOrder;
import com.jukebox.exceptions.PlayListSongsNotFoundException;
import com.jukebox.repositories.IPlayListRepository;
import com.jukebox.repositories.ISongsRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongService implements ISongService{
    private final ISongsRepository songsRepository;
    private final IPlayListRepository playListRepository;

    public SongService(ISongsRepository songsRepository , IPlayListRepository playListRepository) {
        this.songsRepository = songsRepository;
        this.playListRepository = playListRepository;
    }

    @Override
    public void loadSongs(String filename) {
        List<Song> songs = readSongsFromCSV(filename);
        for(Song song : songs){
            songsRepository.save(song);
        }
    }

    private List<Song> readSongsFromCSV(String fileName){
        List<Song> songList = new ArrayList<>();
        try{
            BufferedReader readFile = new BufferedReader(new FileReader(fileName));
            String readFileRow;
            while ((readFileRow = readFile.readLine()) != null){
                String artists = null;
                Song song = null;
                if(!readFileRow.contains(",")){
                    continue;
                }
                String[] data = readFileRow.split(",");
                try{
                    Integer.parseInt(data[0]);
                } catch(NumberFormatException e){
                    //File Row not containing Song ID
                    artists = data[4].replace('#', ',');
                    song = new Song(data[0], data[1], data[2], data[3], artists);
                    songList.add(song);
                    continue;
                }
                //File Row containing Song ID
                artists = data[5].replace('#', ',');
                song = new Song(data[0], data[1], data[2], data[3], data[4], artists);
                songList.add(song);
            }
            readFile.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songList;
    }

    @Override
    public PlayingSongDto playSong(String userId, String songId) {
        //Assuming User ID and Song ID are correct
        String playListId = playListRepository.getCurrentPlayListOnUserID(userId);
        PlayList playList = playListRepository.findById(playListId).get();
        List<String> playListSongs = playList.getPlayListSongs();
        for(String song : playListSongs){
            if(song.equals(songId)){
                Song playingSong = songsRepository.findById(song).get();
                playListRepository.setCurrentSongOnPlayListOfUserID(userId, songId);
                return new PlayingSongDto(playingSong.getSongName(), playingSong.getAlbum(), playingSong.getFeaturedArtists());
            }
        }
        throw new PlayListSongsNotFoundException("Given song id is not a part of the active playlist");
    }

    @Override
    public PlayingSongDto playSong(String userId, SongOrder order) {
        String songId = playListRepository.getCurrentSongOnPlayListOfUserID(userId);
        String playListId = playListRepository.getCurrentPlayListOnUserID(userId);
        PlayList playList = playListRepository.findById(playListId).get();
        List<String> playListSongs = playList.getPlayListSongs();
        int index = 0;
        for(int i = 0 ; i < playListSongs.size() ; i++){
            index = i;
            if(songId.equals(playListSongs.get(i))){
                break;
            }
        }
        switch(order){
            case NEXT :
                index++;
                if(index == playListSongs.size()){  //If Last Song of the Play List, play First Song...
                    index = 0;
                }
                songId = playListSongs.get(index);
                break;
            case BACK :
                index--;
                if(index < 0){                      //If First Song of the PlayList, play First Song again...
                    index = playListSongs.size() - 1;
                }
                songId = playListSongs.get(index);
                break;
        }
        playListRepository.setCurrentSongOnPlayListOfUserID(userId, songId);
        Song playingSong = songsRepository.findById(songId).get();
        return new PlayingSongDto(playingSong.getSongName(), playingSong.getAlbum(), playingSong.getFeaturedArtists());
    }
}
