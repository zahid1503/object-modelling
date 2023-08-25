package com.crio.jukebox.services;

import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.UserRepository;

public class UserService implements IUserService{
    
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User creatUser(String name) {
        return userRepository.save(new User(null, name));
    }
    
}
