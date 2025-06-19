package com.example.notitieblok.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notitieblok.model.Notitie;

public interface NotitieRepository extends JpaRepository<Notitie, Long> {
    List<Notitie> findByUserId(Long userId);  
}


