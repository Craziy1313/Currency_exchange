package org.example.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {


    public static Connection getConnect() {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            URL resource = SQLiteConnection.class.getClassLoader().getResource("CurrencyExchange.db");

            if (resource == null) {
                throw new IllegalArgumentException("База данных не найдена!");
            }
            String dbPath = Paths.get(resource.toURI()).toString();
            String url = "jdbc:sqlite:" + dbPath;

        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                System.out.println("Connection established successfully.");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (SQLException  e) {
            System.out.println("Error while establishing connection: " + e.getMessage());
        }
        return connection;
    } catch (URISyntaxException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
