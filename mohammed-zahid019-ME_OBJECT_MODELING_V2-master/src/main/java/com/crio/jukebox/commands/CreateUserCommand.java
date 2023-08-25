package com.crio.jukebox.commands;


import java.util.List;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.services.UserService;


public class CreateUserCommand implements ICommand {


    private UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }



    @Override
    public void execute(List<String> data) {
        User user = userService.creatUser(data.get(1));
        System.out.println(user.toString());
    }

}

