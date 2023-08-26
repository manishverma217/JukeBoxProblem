package com.jukebox.commands;

import java.util.Arrays;
import java.util.List;
import com.jukebox.entities.SongStatus;
import com.jukebox.services.IPlayListService;

public class ModifyPlayListCommand implements ICommand{

    private final IPlayListService playListService;

    public ModifyPlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }
    // ["MODIFY-PLAYLIST","ADD-SONG","{ USER_ID }","{Playlist-ID }","{ List of Song IDs }"]
    // OR
    // ["MODIFY-PLAYLIST","DELETE-SONG","{ USER_ID }","{Playlist-ID }","{ List of Song IDs }"]
    @Override
    public void execute(List<String> tokens) {
        String songs = tokens.get(4);
        List<String> songsList = Arrays.asList(songs.split(" ", -1));
        try{
            switch(tokens.get(1)){
                case "ADD-SONG" :
                    System.out.println(playListService.modifyPlayList(SongStatus.ADD, tokens.get(2), tokens.get(3), songsList));
                    break;
                case "DELETE-SONG" :
                    System.out.println(playListService.modifyPlayList(SongStatus.DELETE, tokens.get(2), tokens.get(3), songsList));
                    break;
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}

