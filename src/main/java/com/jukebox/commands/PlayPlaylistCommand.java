package com.jukebox.commands;

import java.util.List;
import com.jukebox.services.IPlayListService;

public class PlayPlaylistCommand implements ICommand{

    private final IPlayListService playListService;


    public PlayPlaylistCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }

    // ["PLAY-PLAYLIST","{ USER_ID }","{ Playlist-ID }"]
    @Override
    public void execute(List<String> tokens) {
        try {
            System.out.println(playListService.playPlayListSong(tokens.get(1), tokens.get(2)));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

