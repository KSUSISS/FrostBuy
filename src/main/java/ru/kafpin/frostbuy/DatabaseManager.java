package ru.kafpin.frostbuy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
            instance.connect(); // Сразу пытаемся подключиться при создании
        }
        return instance;
    }

    private void connect() {
        // УКАЖИ СВОИ ДАННЫЕ ОТ ПОСТГРЕСА ЗДЕСЬ:
        String url = "jdbc:postgresql://localhost:5433/frostbuy";
        String user = "postgres"; // твой логин БД
        String password = "1234";  // твой пароль БД

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Подключение к БД успешно установлено!");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}