package ru.kafpin.frostbuy;

import java.time.LocalDateTime;

public class Genre {
    private int id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Genre() {}

    public Genre(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}