package ru.kafpin.frostbuy;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainController {

    @FXML
    private TableView<?> gamesTable; // Позже заменим <?> на <Game>

    private GameDAO gameDAO;

    @FXML
    public void initialize() {
        // Инициализируем DAO, передавая ему соединение из менеджера
        gameDAO = new PostgresGameDAO(DatabaseManager.getInstance().getConnection());

        // Здесь позже добавим загрузку игр в таблицу
        // loadGamesData();
    }
}