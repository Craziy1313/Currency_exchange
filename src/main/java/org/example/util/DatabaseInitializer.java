package org.example.util;

import org.example.dao.CurrenciesDAOImpl;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    static CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();

    public static void createTables() {

        // Запрос для создания таблицы Currencies
        String sqlCreateCourseTable = "CREATE TABLE IF NOT EXISTS Currencies ("
                + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " Code VARCHAR (10) UNIQUE NOT NULL,"
                + " FullName VARCHAR(100) NOT NULL,"
                + " Sign VARCHAR(5) NOT NULL"
                + ");";

        // Запрос для создания таблицы ExchangeRate
        String sqlCreateExchangeRates = "CREATE TABLE IF NOT EXISTS ExchangeRate ("
                + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " BaseCurrencyId INT NOT NULL,"
                + " TargetCurrencyId INT NOT NULL,"
                + " Rate REAL NOT NULL,"
                + " FOREIGN KEY (BaseCurrencyId) REFERENCES Currencies(ID),"
                + " FOREIGN KEY (TargetCurrencyId) REFERENCES Currencies(ID)"
                + ");";

        try (Connection connection = SQLiteConnection.getConnect();
             Statement stmt = connection.createStatement()) {

            stmt.execute(sqlCreateCourseTable);
            stmt.execute(sqlCreateExchangeRates);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tableEnrich() {
        currenciesDAO.crateCurrenciesTable("USD", "US Dollar", "$");
        currenciesDAO.crateCurrenciesTable("EUR", "Euro", "€");
        currenciesDAO.crateCurrenciesTable("RUB", "Russian Ruble", "₽");
        currenciesDAO.crateCurrenciesTable("UAH", "Hryvnia", "₴");
        currenciesDAO.crateCurrenciesTable("KZT", "Tenge", "₸");
        currenciesDAO.crateCurrenciesTable("GBP", "Pound Sterling", "£");
    }
    
    // Основной метод для инициализации базы данных
    public static void initializeDatabase() {
        createTables();// Создание таблиц
        tableEnrich();// Заполнение данными
    }
}
