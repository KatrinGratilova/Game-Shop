package org.katrin.repository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.katrin.MenuMessages;
import org.katrin.entity.Client;
import org.katrin.exception.ClientAlreadyExistsException;
import org.katrin.exception.EntityInstanceDoesNotExist;
import org.katrin.repository.dao.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {
    private final SessionFactory sessionFactory;
    private final String selectWhereContactDataAndPassword = """
            SELECT c
            FROM Client c
            WHERE password = :password AND contactData = :contactData""";

    public ClientRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Client add(Client client) throws ClientAlreadyExistsException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(client);
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {
            session.getTransaction().rollback();
            System.err.println(ex.getMessage());
            throw new ClientAlreadyExistsException(MenuMessages.CLIENT_ALREADY_EXISTS.getMessage(), ex.getCause());
        } finally {
            session.close();
        }
        return client;
    }

    @Override
    public Client getById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Client client = session.get(Client.class, id);
        session.getTransaction().commit();
        session.close();
        return client;
    }

    @Override
    public Client getByContactDataAndPassword(Client client) throws EntityInstanceDoesNotExist {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(selectWhereContactDataAndPassword, Client.class);
        query.setParameter("password", client.getPassword());
        query.setParameter("contactData", client.getContactData());

        Client foundClient;
        try {
            foundClient = (Client) query.getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException ex) {
            session.getTransaction().rollback();
            System.err.println(ex.getMessage());
            throw new EntityInstanceDoesNotExist(MenuMessages.CLIENT_DOES_NOT_EXIST.getMessage(), ex.getCause());
        } finally {
            session.close();
        }
        return foundClient;
    }
}
