package overridetech.jdbc.jpa.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGSERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastName VARCHAR(255) NOT NULL,"
                + "age SMALLINT)";
        try (Session session = sessionFactory.openSession()) {
            session.createNativeMutationQuery(create).executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in creating a table");
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeMutationQuery("DROP TABLE IF EXISTS users CASCADE").executeUpdate();
            transaction.commit();
            logger.log(Level.INFO, "Table dropped");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error dropping table");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (name == null || name.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name and Last Name cannot be empty");
        }
        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);
            session.persist(user);
            logger.log(Level.INFO, "User " + name + " saved successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in saving a user");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                logger.log(Level.INFO, "User " + id + " removed successfully");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in removing a user");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in getting all users");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        String clean = "TRUNCATE TABLE users";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(clean).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in cleaning a table");
            e.printStackTrace();
        }
    }
}
