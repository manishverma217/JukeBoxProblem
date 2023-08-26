package com.jukebox.exceptions;

public class PlayListOnUserNotFoundException extends RuntimeException{
    public PlayListOnUserNotFoundException(){
        super();
    }
    public PlayListOnUserNotFoundException(String msg){
        super(msg);
    }
}