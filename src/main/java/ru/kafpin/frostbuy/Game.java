package ru.kafpin.frostbuy;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Модель сущности "Игра"
 */
public class Game {
    private long id;
    private String title;
    private double price;
    private double discountPrice;

    private Genre genre;            // Связь с жанром
    private String developer;       // Разработчик
    private String publisher;       // Издатель
    private LocalDate releaseDate;  // Дата релиза
    private String platform;        // Платформа (PC, PS5, Xbox и т.д.)
    private String sysRequirements; // Системные требования
    private String description;     // Описание
    private String imagePath;       // Изображение обложки
    private String ageRating;       // Возрастной рейтинг

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Game() {}

    public boolean isOnSale() {
        return discountPrice > 0 && discountPrice < price;
    }

    // --- ГЕТТЕРЫ И СЕТТЕРЫ ---

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getSysRequirements() { return sysRequirements; }
    public void setSysRequirements(String sysRequirements) { this.sysRequirements = sysRequirements; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getAgeRating() { return ageRating; }
    public void setAgeRating(String ageRating) { this.ageRating = ageRating; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}