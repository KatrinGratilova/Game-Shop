package org.katrin.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.katrin.MenuMessages;
import org.katrin.entity.Game;
import org.katrin.exception.EntityInstanceDoesNotExist;
import org.katrin.repository.dao.GameRepository;

import java.util.List;

public class GameRepositoryImpl implements GameRepository {
    private final SessionFactory sessionFactory;
    private final String selectAll = """
            SELECT g
            FROM Game g
            """;
    private final String selectWhereName = """
            SELECT g
            FROM Game g
            WHERE name = :name
            """;
    private final String selectWhereCostBetween = """
            SELECT g
            FROM Game g
            WHERE cost BETWEEN :min AND :max
            """;
    private final String selectWhereType = """
            SELECT g
            FROM Game g
            WHERE type = :type
            """;
    private final String selectSortedByCreationDate = """
            SELECT g
            FROM Game g
            ORDER BY creationDate
            """;

    public GameRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Game add(Game game) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(game);
        session.getTransaction().commit();
        session.close();
        return game;
    }

    @Override
    public void deleteById(int id) throws EntityInstanceDoesNotExist {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Game game = session.get(Game.class, id);
        try {
            session.remove(game);
            session.getTransaction().commit();
        } catch (IllegalArgumentException ex) {
            session.getTransaction().rollback();
            System.err.println(ex.getMessage());
            throw new EntityInstanceDoesNotExist(MenuMessages.GAME_DOES_NOT_EXIST.getMessage(), ex.getCause());
        } finally {
            session.close();
        }
    }

    @Override
    public List<Game> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Game> query = session.createQuery(selectWhereName, Game.class);
            query.setParameter("name", name);
            session.getTransaction().commit();
            return query.list();
        }
    }

    @Override
    public List<Game> getByCostRange(double min, double max) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Game> query = session.createQuery(selectWhereCostBetween, Game.class);
            query.setParameter("min", min);
            query.setParameter("max", max);
            session.getTransaction().commit();
            return query.list();
        }
    }

    @Override
    public List<Game> getByType(String type) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Game> query = session.createQuery(selectWhereType, Game.class);
            query.setParameter("type", type);
            session.getTransaction().commit();
            return query.list();
        }
    }

    @Override
    public List<Game> sortByCreationDate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Game> query = session.createQuery(selectSortedByCreationDate, Game.class);
            session.getTransaction().commit();
            return query.list();
        }
    }

    @Override
    public List<Game> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Game> query = session.createQuery(selectAll, Game.class);
            session.getTransaction().commit();
            return query.list();
        }
    }
}
