package org.katrin.repository.dao;

import org.katrin.entity.Game;
import org.katrin.exception.EntityInstanceDoesNotExist;

import java.util.List;

public interface GameRepository {
    Game add(Game game);

    void deleteById(int id) throws EntityInstanceDoesNotExist;

    List<Game> getByName(String name);

    List<Game> getByCostRange(double min, double max);

    List<Game> getByType(String type);

    List<Game> sortByCreationDate();

    List<Game> getAll();
}
