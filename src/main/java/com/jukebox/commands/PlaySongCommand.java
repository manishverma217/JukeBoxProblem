package com.jukebox.commands;

import java.util.List;
import com.jukebox.entities.SongOrder;
import com.jukebox.services.ISongService;

public class PlaySongCommand implements ICommand{

    private final ISongService songService;

    public PlaySongCommand(ISongService songService) {
        this.songService = songService;
    }
    // ["PLAY-SONG","{ USER_ID }","{ Song ID }"]
    // ["PLAY-SONG","{ USER_ID }","NEXT"]
    // OR
    // ["PLAY-SONG","{ USER_ID }","BACK"]
    @Override
    public void execute(List<String> tokens) {
        try {
            if(tokens.get(2).equalsIgnoreCase(SongOrder.BACK.name())){
                System.out.println(songService.playSong(tokens.get(1), SongOrder.BACK));
            } else if(tokens.get(2).equalsIgnoreCase(SongOrder.NEXT.name())){
                System.out.println(songService.playSong(tokens.get(1), SongOrder.NEXT));
            } else {
                System.out.println(songService.playSong(tokens.get(1), tokens.get(2)));
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

