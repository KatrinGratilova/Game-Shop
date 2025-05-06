package org.katrin.service;

import org.katrin.entity.Game;
import org.katrin.exception.EntityInstanceDoesNotExist;
import org.katrin.repository.dao.GameRepository;

import java.util.List;

public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game create(Game game) {
        return repository.add(game);
    }

    public void deleteById(int id) throws EntityInstanceDoesNotExist {
        repository.deleteById(id);
    }

    public List<Game> findByName(String name) {
        return repository.getByName(name);
    }

    public List<Game> findByCostRange(double min, double max) {
        return repository.getByCostRange(min, max);
    }

    public List<Game> findByType(String type) {
        return repository.getByType(type);
    }

    public List<Game> sortByCreationDate() {
        return repository.sortByCreationDate();
    }

    public List<Game> findAll() {
        return repository.getAll();
    }
}
