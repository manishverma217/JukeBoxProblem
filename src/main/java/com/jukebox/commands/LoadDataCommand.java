package com.jukebox.commands;

import java.util.List;
import com.jukebox.services.ISongService;

public class LoadDataCommand implements ICommand{
    private final ISongService songService;

    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }

    // ["LOAD-DATA","SONGS.CSV"]
    @Override
    public void execute(List<String> tokens) {
        try {
            songService.loadSongs(tokens.get(1));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Songs Loaded successfully");
    }
}

