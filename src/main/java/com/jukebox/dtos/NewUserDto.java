package com.jukebox.dtos;

public class NewUserDto {
    private final String userId;
    private final String userName;

    public NewUserDto(String userId , String userName){
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return userId + " " + userName;
    }

}
