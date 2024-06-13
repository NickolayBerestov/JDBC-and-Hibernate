package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory connection = Util.getHibernateConnection();


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = connection.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGSERIAL Primary key," +
                    "name VARCHAR(255)," +
                    "last_name VARCHAR(255)," +
                    "age SMALLINT)").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = connection.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = connection.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = connection.openSession()) {
            session.beginTransaction();
            User user = Optional.ofNullable(session.get(User.class, id)).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            session.delete(user);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = connection.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = connection.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
