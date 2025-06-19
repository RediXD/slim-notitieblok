package com.example.notitieblok.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notitie")
public class Notitie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datum;

    private String titel;
    private String inhoud;
    private String deadline;
    private String label;


    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private boolean gearchiveerd = false;
    


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Gettery i settery

public LocalDateTime getDatum() {
    return datum;
}

public void setDatum(LocalDateTime datum) {
    this.datum = datum;
}

public String getDeadline() {
    return deadline;
}

public void setDeadline(String deadline) {
    this.deadline = deadline;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitel() {
    return titel;
}

public void setTitel(String titel) {
    this.titel = titel;
}

public String getInhoud() {
    return inhoud;
}

public void setInhoud(String inhoud) {
    this.inhoud = inhoud;
}

public boolean isGearchiveerd() {
    return gearchiveerd;
}

public void setGearchiveerd(boolean gearchiveerd) {
    this.gearchiveerd = gearchiveerd;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}
public String getLabel() {
    return label;
}

public void setLabel(String label) {
    this.label = label;
}

}



