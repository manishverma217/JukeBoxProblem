package com.jukebox.dtos;

import java.util.List;

public class PlayListDto {
    private final String playListId;
    private final String playListName;
    private final List<String> playListSongs;

    public PlayListDto(String playListId, String playListName, List<String> playListSongs) {
        this.playListId = playListId;
        this.playListName = playListName;
        this.playListSongs = playListSongs;
    }

    public String getPlayListId() {
        return playListId;
    }

    public String getPlayListName() {
        return playListName;
    }

    public List<String> getPlayListSongs() {
        return playListSongs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String song : playListSongs){
            sb.append(song + " ");
        }
        return "Playlist ID - " + playListId + "\n" +
                "Playlist Name - " + playListName + "\n" +
                "Song IDs - " + sb.toString().trim();
    }


}

