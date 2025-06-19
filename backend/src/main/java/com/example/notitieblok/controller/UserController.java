package com.example.notitieblok.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notitieblok.model.User;
import com.example.notitieblok.repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@PostMapping("/login")
public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
    String username = credentials.get("username");
    String password = credentials.get("password");

    // Find the first user with the given username
    java.util.Optional<User> userOpt = userRepository.findAllByUsername(username).stream().findFirst();

    Map<String, Object> result = new HashMap<>();

    if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
        result.put("loggedIn", true);
        result.put("userId", userOpt.get().getId());
    } else {
        result.put("loggedIn", false);
    }

    return result;
}

}






