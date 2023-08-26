package com.jukebox.commands;

import java.util.List;
import com.jukebox.services.IPlayListService;

public class DeletePlayListCommand implements ICommand{

    private final IPlayListService playListService;

    public DeletePlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }

    // ["DELETE-PLAYLIST","{ USER_ID }","{ Playlist-ID }"]
    @Override
    public void execute(List<String> tokens) {
        try{
            playListService.deletePlayList(tokens.get(1), tokens.get(2));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
