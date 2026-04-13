package ru.kafpin.frostbuy;

import java.util.List;
import java.util.Optional;


public interface GameDAO {

    Optional<Game> getById(Long id);


    List<Game> getAll();


    void add(Game game);


    void update(Game game);

    void delete(Long id);
    List<Game> findByTitle(String title);
    List<Game> findByGenre(Genre genre);
}