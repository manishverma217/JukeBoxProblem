package com.jukebox.entities;

import java.util.List;

public class PlayList {
    private String playListId;
    private String playListName;
    private List<String> playListSongs;
    private String userId;

    public PlayList(PlayList playList) {
        this(playList.playListId, playList.playListName, playList.playListSongs, playList.userId);
    }

    public PlayList(String playListId, String playListName, List<String> playListSongs, String userId) {
        this(playListName, playListSongs, userId);
        this.playListId = playListId;
    }

    public PlayList(String playListName, List<String> playListSongs, String userId) {
        this.playListName = playListName;
        this.playListSongs = playListSongs;
        this.userId = userId;
    }

    public String getPlayListId() {
        return this.playListId;
    }

    public String getPlayListName() {
        return this.playListName;
    }

    public List<String> getPlayListSongs() {
        return this.playListSongs;
    }

    public void setPlayListSongs(List<String> playListSongs) {
        this.playListSongs = playListSongs;
    }

    public String getUserId() {
        return this.userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((playListId == null) ? 0 : playListId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayList other = (PlayList) obj;
        if (playListId == null) {
            if (other.playListId != null)
                return false;
        } else if (!playListId.equals(other.playListId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Playlist ID - " + playListId;
    }
}
