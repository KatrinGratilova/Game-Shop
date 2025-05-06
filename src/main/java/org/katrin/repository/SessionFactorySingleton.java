package org.katrin.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.katrin.entity.Client;
import org.katrin.entity.Game;

public class SessionFactorySingleton {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/game_shop");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "230218");

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("show_sql", true);

            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Game.class);

            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
}
