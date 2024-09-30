package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "Admin";

    public static Connection getConnection() {
        Connection connect = null;
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                connect = connection;
            }
        } catch (SQLException e) {
            System.out.println("there is no connection... Exception!");
        }
        return connect;
    }

}
