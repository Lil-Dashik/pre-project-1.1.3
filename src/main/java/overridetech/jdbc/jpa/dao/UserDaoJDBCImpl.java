package overridetech.jdbc.jpa.dao;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGSERIAL PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastName VARCHAR(255) NOT NULL,"
                + "age SMALLINT)";
        try (Connection conection = Util.connectToDatebase();
             PreparedStatement statement = conection.prepareStatement(create)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Not create table", e);
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.connectToDatebase();
             PreparedStatement statement = connection.prepareStatement(drop)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Not drop table", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
        try (Connection connection = Util.connectToDatebase();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User with the name – " + name+ " added to the database");
        } catch (SQLException e) {
            throw new RuntimeException("Not save user", e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?;";
        try (Connection connection = Util.connectToDatebase();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Not remove user", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try (Connection connection = Util.connectToDatebase();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e){
            throw new RuntimeException("Not get users", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE users;";
        try (Connection connection = Util.connectToDatebase();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Not clean table", e);
        }
    }
}
