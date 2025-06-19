package com.example.notitieblok.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.notitieblok.model.Notitie;
import com.example.notitieblok.model.User;
import com.example.notitieblok.repository.NotitieRepository;
import com.example.notitieblok.repository.UserRepository;

@RestController
@RequestMapping("/api/notities")
@CrossOrigin(origins = "http://localhost:5173")
public class NotitieController {

    private final NotitieRepository notitieRepository;
    private final UserRepository userRepository;

    public NotitieController(NotitieRepository notitieRepository, UserRepository userRepository) {
        this.notitieRepository = notitieRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public List<Notitie> getAllByUser(@PathVariable Long userId) {
        return notitieRepository.findByUserId(userId);
    }

    @PostMapping("/{userId}")
    public Notitie addNotitie(@PathVariable Long userId, @RequestBody Notitie notitie) {
        User user = userRepository.findById(userId).orElseThrow();
        notitie.setUser(user);

        if (notitie.getLabel() == null || notitie.getLabel().isEmpty()) {
            notitie.setLabel("onbekend");
        }

        return notitieRepository.save(notitie);
    }

    @PutMapping("/{id}/archive")
    public Notitie archiveNotitie(@PathVariable Long id) {
        Notitie notitie = notitieRepository.findById(id).orElseThrow();
        notitie.setGearchiveerd(true);
        return notitieRepository.save(notitie);
    }

    @DeleteMapping("/{id}")
    public void deleteNotitie(@PathVariable Long id) {
        notitieRepository.deleteById(id);
    }
}


 









