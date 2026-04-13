package ru.kafpin.frostbuy;

import java.sql.*;
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
        String sql = "SELECT g.*, gen.title as genre_name FROM games g " +
                "JOIN genres gen ON g.genre_id = gen.id";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                // Используем наш новый метод для сборки объекта
                games.add(mapRowToGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public Optional<Game> getById(Long id) {
        String sql = "SELECT g.*, gen.title as genre_name FROM games g " +
                "JOIN genres gen ON g.genre_id = gen.id WHERE g.id = ?";
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
        String sql = "INSERT INTO games (title, price, stock_quantity, genre_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, game.getTitle());
            ps.setDouble(2, game.getPrice());
            ps.setInt(3, game.getStockQuantity());
            ps.setInt(4, game.getGenre().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Game game) {
        String sql = "UPDATE games SET title=?, price=?, stock_quantity=?, genre_id=?, updated_at=CURRENT_TIMESTAMP WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, game.getTitle());
            ps.setDouble(2, game.getPrice());
            ps.setInt(3, game.getStockQuantity());
            ps.setInt(4, game.getGenre().getId());
            ps.setLong(5, game.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM games WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Game> findByTitle(String title) {
        List<Game> results = new ArrayList<>();
        String sql = "SELECT g.*, gen.title as genre_name FROM games g " +
                "JOIN genres gen ON g.genre_id = gen.id " +
                "WHERE g.title ILIKE ?";

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
        return List.of();
    }

    /**
     * Вспомогательный метод для сборки объекта Game из ResultSet.
     * Именно здесь реализуется логика JOIN (стр. 2-3 методички).
     */
    private Game mapRowToGame(ResultSet rs) throws SQLException {
        // 1. Сначала собираем вложенный объект Genre (стр. 2)
        Genre genre = new Genre(
                rs.getInt("genre_id"),
                rs.getString("genre_name")
        );

        // 2. Извлекаем даты из PostgreSQL
        LocalDateTime created = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updated = rs.getTimestamp("updated_at").toLocalDateTime();

        // 3. Возвращаем полностью собранный объект Game (стр. 3)
        return new Game(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity"),
                genre,
                created,
                updated
        );
    }
}