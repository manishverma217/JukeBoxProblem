package com.jukebox.services;

import com.jukebox.dtos.PlayListDto;
import com.jukebox.dtos.PlayingSongDto;
import com.jukebox.entities.PlayList;
import com.jukebox.entities.SongStatus;
import com.jukebox.exceptions.PlayListNotFoundException;
import com.jukebox.exceptions.PlayListOnUserNotFoundException;
import com.jukebox.exceptions.UserNotFoundException;

import java.util.List;

public interface IPlayListService {
    public PlayList createPlayList(String userId , String playListName , List<String> playListSongs) throws UserNotFoundException , PlayListNotFoundException;
    public PlayList deletePlayList(String userId , String playListId) throws UserNotFoundException, PlayListNotFoundException, PlayListOnUserNotFoundException;
    public PlayListDto modifyPlayList(SongStatus status , String userId , String playListId , List<String> playListSongs);
    public PlayList updatePlayList(PlayList oldPlayList , PlayList newPlayList);
    public PlayingSongDto playPlayListSong(String userId , String playListId);
}