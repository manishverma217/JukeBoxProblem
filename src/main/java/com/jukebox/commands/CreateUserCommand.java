package com.jukebox.commands;

import com.jukebox.services.IUserService;

import java.util.List;

public class CreateUserCommand implements ICommand {

    private final IUserService userService;

    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    // ["CREATE-USER","{ User_Name }"]
    @Override
    public void execute(List<String> tokens) {
        System.out.println(userService.create(tokens.get(1)));
    }
}