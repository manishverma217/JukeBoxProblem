package com.jukebox.commands;

import com.jukebox.services.IPlayListService;

import java.util.ArrayList;
import java.util.List;

public class CreatePlayListCommand implements ICommand{
    private final IPlayListService playListService;

    public CreatePlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }

    // ["CREATE-PLAYLIST","{ USER_ID }","{ PLAYLIST_NAME }","{ List of Song IDs }"]
    @Override
    public void execute(List<String> tokens) {
        try {
            List<String> songsList = new ArrayList<>();
            for(int i = 3 ; i < tokens.size() ; i++){
                songsList.add(tokens.get(i));
            }
            System.out.println(playListService.createPlayList(tokens.get(1), tokens.get(2), songsList));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
