package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.Statement;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class UserHibernateDaoImpl implements UserDao {
    public UserHibernateDaoImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.doWork(connection -> {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS User (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(50), " +
                        "lastName VARCHAR(50), " +
                        "age TINYINT)"
                    );
                }
            });
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.doWork(connection -> {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("DROP TABLE IF EXISTS User");
                }
            });
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                User user = session.get(User.class, id);

                if (user != null) {
                    session.delete(user);
                }

                transaction.commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.doWork(connection -> {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("DELETE FROM User");
                }
            });
        }
    }
}
