package com.jukebox.services;

import com.jukebox.dtos.NewUserDto;

public interface IUserService {
    public NewUserDto create(String userName);
}
