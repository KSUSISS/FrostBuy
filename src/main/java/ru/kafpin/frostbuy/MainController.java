package ru.kafpin.frostbuy;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private FlowPane gamesFlowPane;

    private GameDAO gameDAO;

    @FXML
    public void initialize() {
        gameDAO = new PostgresGameDAO(DatabaseManager.getInstance().getConnection());
        loadGamesData();
    }

    private void loadGamesData() {
        gamesFlowPane.getChildren().clear();

        for (Game game : gameDAO.getAll()) {
            VBox card = createGameCard(game);
            gamesFlowPane.getChildren().add(card);
        }
    }


    private VBox createGameCard(Game game) {
        VBox card = new VBox(10);
        card.getStyleClass().add("game-card");
        card.setPrefWidth(220);
        card.setPrefHeight(300);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_LEFT);

        // Название игры
        Label titleLabel = new Label(game.getTitle());
        titleLabel.getStyleClass().add("card-title");
        titleLabel.setWrapText(true);

        // Жанр и платформа
        String genreName = game.getGenre() != null ? game.getGenre().getTitle() : "Нет жанра";
        Label infoLabel = new Label(genreName + " | " + game.getPlatform());
        infoLabel.getStyleClass().add("card-info");

        // Цена
        Label priceLabel = new Label(String.format("%,.2f ₽", game.getPrice()));
        priceLabel.getStyleClass().add("card-price");

        // Кнопки управления
        Button editBtn = new Button("✏");
        editBtn.getStyleClass().add("action-button");
        Button deleteBtn = new Button("🗑");
        deleteBtn.getStyleClass().add("action-button-danger");

        HBox actionBox = new HBox(10, editBtn, deleteBtn);
        actionBox.setAlignment(Pos.BOTTOM_RIGHT);
        VBox.setVgrow(actionBox, javafx.scene.layout.Priority.ALWAYS); // Прижимаем кнопки к низу

        // Собираем всё в карточку
        card.getChildren().addAll(titleLabel, infoLabel, priceLabel, actionBox);
        return card;
    }
}