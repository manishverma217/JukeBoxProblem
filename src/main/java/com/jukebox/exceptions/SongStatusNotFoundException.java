package com.jukebox.exceptions;

public class SongStatusNotFoundException extends RuntimeException {
    public SongStatusNotFoundException(){
        super();
    }
    public SongStatusNotFoundException(String msg){
        super(msg);
    }
}
