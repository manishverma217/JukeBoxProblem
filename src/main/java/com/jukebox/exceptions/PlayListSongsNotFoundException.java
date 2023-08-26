package com.jukebox.exceptions;

public class PlayListSongsNotFoundException extends RuntimeException{
    public PlayListSongsNotFoundException(){
        super();
    }
    public PlayListSongsNotFoundException(String msg){
        super(msg);
    }
}
