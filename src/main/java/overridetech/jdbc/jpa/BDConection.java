package overridetech.jdbc.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConection {
    public static Connection connectToDatebase() {
        try {
            String url = "jdbc:postgresql://localhost:5432/users";
            String user = "postgres";
            String password = "1234";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the user.", e);
        }
    }
}
