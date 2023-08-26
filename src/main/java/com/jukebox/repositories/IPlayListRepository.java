package com.jukebox.repositories;

import com.jukebox.entities.PlayList;

public interface IPlayListRepository extends CRUDRepository<PlayList , String>{
    public PlayList findBy_PlayListID_UserID(String playListId , String userId);
    public void setCurrentPlayListOnUserID(String userId , String playListId , String songId);
    public String getCurrentPlayListOnUserID(String userId);
    public void setCurrentSongOnPlayListOfUserID(String userId , String songId);
    public String getCurrentSongOnPlayListOfUserID(String userId);
}
