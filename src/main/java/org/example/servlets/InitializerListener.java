package org.example.servlets;

import org.example.DAO.*;
import org.example.util.DatabaseInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

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
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("JDBC-драйвер успешно снят с регистрации: " + driver);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Ошибка при снятии регистрации JDBC-драйвера: " + driver);
            }
        }
    }
}
