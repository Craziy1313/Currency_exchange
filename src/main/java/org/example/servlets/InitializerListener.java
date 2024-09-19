package org.example.servlets;

import org.example.util.DatabaseInitializer;
import org.example.DAO.SQLiteConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class InitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try (Connection connection = SQLiteConnection.getConnect()) {
            if (connection != null) {
                System.out.println("База данных успешно подключена");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DatabaseInitializer.initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
