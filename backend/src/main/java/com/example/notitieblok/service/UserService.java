package com.example.notitieblok.service;

import org.springframework.stereotype.Service;

import com.example.notitieblok.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        return userRepository.findAllByUsername(username)
                .stream()
                .findFirst()
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}




