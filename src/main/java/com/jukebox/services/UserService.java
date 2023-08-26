package com.jukebox.services;

import com.jukebox.dtos.NewUserDto;
import com.jukebox.entities.User;
import com.jukebox.repositories.IUserRepository;

public class UserService implements IUserService{

    private final IUserRepository userRepository;


    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public NewUserDto create(String userName) {
        Integer userId = userRepository.findAll().size() + 1;
        User user = new User(userId.toString(), userName);
        userRepository.save(user);
        return new NewUserDto(Integer.toString(userId), userName);
    }

}
