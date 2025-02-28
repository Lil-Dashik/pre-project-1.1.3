package overridetech.jdbc.jpa.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import overridetech.jdbc.jpa.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:postgresql://localhost:5432/users";
    private static final String user = "postgres";
    private static final String password = "1234";

    public static Connection connectToDatabase() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the user.", e);
        }
    }

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry builder = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .applySetting("hibernate.connection.url", url)
                    .applySetting("hibernate.connection.username", user)
                    .applySetting("hibernate.connection.password", password)
                    .applySetting("hibernate.connection.autocommit", "true")
                    .applySetting("hibernate.hbm2ddl.auto", "update")
                    .build();
            Metadata metadata = new MetadataSources(builder)
                    .addAnnotatedClass(User.class)
                    .buildMetadata();
            return metadata.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Hibernate initialization error:" + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
