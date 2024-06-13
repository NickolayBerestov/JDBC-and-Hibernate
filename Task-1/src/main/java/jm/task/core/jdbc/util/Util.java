package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getNewConnection() {
        try {
            Properties properties = getProperties();

            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();

        properties.load(Util.class.getClassLoader().getResourceAsStream("application.properties"));
        return properties;
    }

    public static SessionFactory getHibernateConnection() {
        try {
            Properties properties = getProperties();
            return new Configuration()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory(new StandardServiceRegistryBuilder()
                            .applySetting("hibernate.connection.url", properties.getProperty("url"))
                            .applySetting("hibernate.connection.username", properties.getProperty("username"))
                            .applySetting("hibernate.connection.password", properties.getProperty("password"))
                            .applySetting("hibernate.show_sql", properties.getProperty("hibernate.show_sql"))
                            .build());
        } catch (Throwable e) {
            System.out.println("Не удалось подключиться к базе");
            throw new ExceptionInInitializerError(e);
        }
    }
}
