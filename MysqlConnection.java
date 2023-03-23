package com.flight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    private static Connection connection = null;

    public static Connection getMysql() throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/airReservation";
            String userName = "root";
            String userPassword = "ananthi";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, userPassword);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return connection;
    }

    public static void conClose() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
