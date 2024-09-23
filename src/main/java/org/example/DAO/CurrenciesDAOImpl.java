package org.example.DAO;

import org.example.models.Currencies;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrenciesDAOImpl implements CurrenciesDAO{

    private static final Logger logger = Logger.getLogger(CurrenciesDAOImpl.class.getName());

    @Override
    public void crateCurrenciesTable() {
        try (Connection connection = SQLiteConnection.getConnect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Currencies ("
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " Code VARCHAR (10) UNIQUE NOT NULL,"
                    + " FullName VARCHAR(100) NOT NULL,"
                    + " Sign VARCHAR(5) NOT NULL"
                    + ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void deleteCurrenciesTable() {
        try (Connection connection = SQLiteConnection.getConnect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Currencies");
            System.out.println("Currencies table deleted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveCurrencies(String code, String fullName, String sign) throws SQLException {

        try (Connection connection = SQLiteConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Currencies (code, fullname, sign) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, code);
            preparedStatement.setString(2, fullName);
            preparedStatement.setString(3, sign);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE.code) {
                String message = "Код валюты: '" + code + "' уже внесен в базу данных";
                throw new SQLException(message, e);
            } else {
                throw e;
            }
        }

    }

    @Override
    public void updateCurrencies(Currencies currencies) {
        try (Connection connection = SQLiteConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Currencies (id, code, fullname, sign) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, currencies.getId());
            preparedStatement.setString(2, currencies.getCode());
            preparedStatement.setString(3, currencies.getName());
            preparedStatement.setString(3, currencies.getSign());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving currency: " + e.getMessage(), e);
        }

    }

    @Override
    public Optional<Currencies> getCurrenciesByCode(String code) {
        Currencies currency = null;
        try (Connection connection = SQLiteConnection.getConnect()) {
            String query = "SELECT Id, Code, FullName, Sign FROM Currencies WHERE Code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currency = getCurrenciesFromQuery(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(currency);
    }

    @Override
    public Currencies getCurrenciesById(int id) {
        Currencies currency = null;
        try (Connection connection = SQLiteConnection.getConnect()) {
            String query = "SELECT Id, Code, FullName, Sign FROM Currencies WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currency = getCurrenciesFromQuery(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return currency;
    }

    @Override
    public List<Currencies> getAllCurrencies() {

        List<Currencies> list = new ArrayList<>();
        try (Connection connection = SQLiteConnection.getConnect()) {
            logger.info("Connected to database: " + connection.getMetaData().getURL());

            Statement statement = connection.createStatement();
            String query = "SELECT Id, Code, FullName, Sign FROM Currencies";
            logger.info("Executing query: " + query);

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(getCurrenciesFromQuery(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error: " + e.getMessage(), e);
        }
        return list;
    }

    private Currencies getCurrenciesFromQuery(ResultSet result) throws SQLException {

        Currencies currencies = new Currencies();

        currencies.setId(result.getInt("Id"));
        currencies.setCode(result.getString("Code"));
        currencies.setName(result.getString("FullName"));
        currencies.setSign(result.getString("Sign"));

        return currencies;
    }
}
