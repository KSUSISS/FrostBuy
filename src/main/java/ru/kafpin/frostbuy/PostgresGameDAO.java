package ru.kafpin.frostbuy;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresGameDAO implements GameDAO {
    private final Connection connection;

    public PostgresGameDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Game> getAll() {
        List<Game> games = new ArrayList<>();
        // Вызов функции БД вместо прямого селекта (Требование 3.2)
        String sql = "SELECT * FROM get_all_games()";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                games.add(mapRowToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public Optional<Game> getById(Long id) {
        String sql = "SELECT * FROM get_game_by_id(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void add(Game game) {
        // Вызов хранимой процедуры БД (Требование 3.2)
        String sql = "{CALL add_game(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.setString(1, game.getTitle());
            cs.setDouble(2, game.getPrice());
            cs.setInt(3, game.getGenre().getId());
            cs.setString(4, game.getDeveloper());
            cs.setString(5, game.getPublisher());
            cs.setDate(6, game.getReleaseDate() != null ? java.sql.Date.valueOf(game.getReleaseDate()) : null);
            cs.setString(7, game.getPlatform());
            cs.setString(8, game.getSysRequirements());
            cs.setString(9, game.getDescription());
            cs.setString(10, game.getImagePath());
            cs.setString(11, game.getAgeRating());
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Game game) {
        // Вызов хранимой процедуры БД (Требование 3.2)
        String sql = "{CALL update_game(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.setLong(1, game.getId());
            cs.setString(2, game.getTitle());
            cs.setDouble(3, game.getPrice());
            cs.setInt(4, game.getGenre().getId());
            cs.setString(5, game.getDeveloper());
            cs.setString(6, game.getPublisher());
            cs.setDate(7, game.getReleaseDate() != null ? java.sql.Date.valueOf(game.getReleaseDate()) : null);
            cs.setString(8, game.getPlatform());
            cs.setString(9, game.getSysRequirements());
            cs.setString(10, game.getDescription());
            cs.setString(11, game.getImagePath());
            cs.setString(12, game.getAgeRating());
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        // Вызов хранимой процедуры БД (Требование 3.2)
        String sql = "{CALL delete_game(?)}";
        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.setLong(1, id);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Game> findByTitle(String title) {
        List<Game> results = new ArrayList<>();
        String sql = "SELECT * FROM search_games_by_title(?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(mapRowToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<Game> findByGenre(Genre genre) {
        // Заглушка, можно реализовать аналогично findByTitle
        return List.of();
    }

    /**
     * Вспомогательный метод для сборки объекта Game из ResultSet.
     */
    private Game mapRowToGame(ResultSet rs) throws SQLException {
        // Создаем жанр
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setTitle(rs.getString("genre_name"));

        // Создаем игру
        Game game = new Game();
        game.setId(rs.getLong("id"));
        game.setTitle(rs.getString("title"));
        game.setPrice(rs.getDouble("price"));
        game.setGenre(genre);
        game.setDeveloper(rs.getString("developer"));
        game.setPublisher(rs.getString("publisher"));

        // Обработка даты релиза
        Date releaseDate = rs.getDate("release_date");
        if (releaseDate != null) {
            game.setReleaseDate(releaseDate.toLocalDate());
        }

        game.setPlatform(rs.getString("platform"));
        game.setSysRequirements(rs.getString("system_requirements"));
        game.setDescription(rs.getString("description"));
        game.setImagePath(rs.getString("image_path"));
        game.setAgeRating(rs.getString("age_rating"));

        // Обработка дат создания/обновления
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            game.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            game.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return game;
    }
}