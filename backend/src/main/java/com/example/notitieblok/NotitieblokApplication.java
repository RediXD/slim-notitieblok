package com.example.notitieblok;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.notitieblok.model.User;
import com.example.notitieblok.repository.UserRepository;

@SpringBootApplication
public class NotitieblokApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotitieblokApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepository userRepo) {
        return args -> {
            if (userRepo.findAllByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                userRepo.save(admin);
            }
        };
    }
}




