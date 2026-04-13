package ru.kafpin.frostbuy;

import java.time.LocalDateTime;

/**
 * Модель сущности "Игра"
 */
public class Game {
    private long id;
    private String title;
    private double price;
    private int stockQuantity;
    private Genre genre; // Связь с жанром (стр. 2 методички)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private double discountPrice;


    public boolean isOnSale() {
        return discountPrice > 0 && discountPrice < price;
    }

    // Пустой конструктор для POJO
    public Game() {}

    // Конструктор со всеми полями
    public Game(long id, String title, double price, int stockQuantity, Genre genre,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.genre = genre;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    private String imagePath;




    // Геттеры и сеттеры
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}