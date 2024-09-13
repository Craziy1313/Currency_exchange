package org.example.DAO;

import org.example.models.ExchangeRates;
import org.example.util.SQLiteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeRatesDAOImpl implements ExchangeRatesDAO{

    CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();
    private static final Logger logger = Logger.getLogger(ExchangeRates.class.getName());

    @Override
    public void crateExchangeRatesTable() {
        try (Connection connection = SQLiteConnection.getConnect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ExchangeRate ("
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " BaseCurrencyId INT NOT NULL,"
                    + " TargetCurrencyId INT NOT NULL,"
                    + " Rate Decimal(6) NOT NULL,"
                    + " FOREIGN KEY (BaseCurrencyId) REFERENCES Currencies(ID),"
                    + " FOREIGN KEY (TargetCurrencyId) REFERENCES Currencies(ID)"
                    + ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void deleteExchangeRatesTable() {
        try (Connection connection = SQLiteConnection.getConnect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE ExchangeRate");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

        @Override
    public void saveExchangeRates (int baseCurrencyId, int targetCurrencyId, Double rate) {
        String sqlInsertCurrency = "INSERT INTO ExchangeRate (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?)";

        try (Connection connection = SQLiteConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCurrency)) {

            preparedStatement.setString(1, String.valueOf(baseCurrencyId));
            preparedStatement.setString(2, String.valueOf(targetCurrencyId));
            preparedStatement.setString(3, String.valueOf(rate));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void updateExchangeRates(ExchangeRates exchangeRates) {

    }

    @Override
    public ExchangeRates getExchangeRates(int id) {
        ExchangeRates exchangeRates = null;
        try (Connection connection = SQLiteConnection.getConnect()) {
            String query = "SELECT Id, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRate WHERE Id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            exchangeRates = getExchangeFromQuery(resultSet);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exchangeRates;
    }

    @Override
    public List<ExchangeRates> getAllExchangeRates() {
        List<ExchangeRates> list = new ArrayList<>();
        try (Connection connection = SQLiteConnection.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT Id, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRate");

            while (resultSet.next()) {
                list.add(getExchangeFromQuery(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    private ExchangeRates getExchangeFromQuery(ResultSet result) throws SQLException {

        ExchangeRates exchangeRates = new ExchangeRates();
        exchangeRates.setID(result.getInt("Id"));
        exchangeRates.setBaseCurrency(currenciesDAO.getCurrenciesById(result.getInt("BaseCurrencyId")));
        exchangeRates.setTargetCurrency(currenciesDAO.getCurrenciesById(result.getInt("TargetCurrencyId")));

        exchangeRates.setRate(result.getDouble("Rate"));

        return exchangeRates;
    }
}
