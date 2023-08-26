package com.jukebox.services;

import com.jukebox.dtos.PlayingSongDto;
import com.jukebox.entities.SongOrder;

public interface ISongService {
    public void loadSongs(String filename);
    public PlayingSongDto playSong(String userId , String songId);
    public PlayingSongDto playSong(String userId , SongOrder order);
}

