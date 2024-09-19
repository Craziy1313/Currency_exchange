package org.example.DAO;

import org.example.models.Currencies;
import org.example.models.ExchangeRates;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                    + " UNIQUE (BaseCurrencyId, TargetCurrencyId)"
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
    public void saveExchangeRates (String baseCurrencyId, String targetCurrencyId, Double rate) throws SQLException {
        String sqlInsertCurrency = "INSERT INTO ExchangeRate (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?)";

        Optional<Currencies> BaseCurrency = currenciesDAO.getCurrenciesByCode(baseCurrencyId);
        Optional<Currencies> TargetCurrency = currenciesDAO.getCurrenciesByCode(targetCurrencyId);

        Optional <ExchangeRates> exchangeRatesOption = getExchangeRatesByCode(targetCurrencyId, baseCurrencyId);
        if (exchangeRatesOption.isPresent()) {
            throw new SQLException("Валютная пара с таким кодом уже существует");
        }

        if (BaseCurrency.isPresent() && TargetCurrency.isPresent()) {
            Currencies baseCurrency = BaseCurrency.get();
            Currencies targetCurrency = TargetCurrency.get();


            try (Connection connection = SQLiteConnection.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCurrency)) {

                preparedStatement.setInt(1, baseCurrency.getId());
                preparedStatement.setInt(2, targetCurrency.getId());
                preparedStatement.setDouble(3, rate);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE.code) {
                    String message = "Валютная пара с таким кодом уже существует";
                    throw new SQLException(message, e);
                } else {
                    throw e;
                }
            }
        }
    }

    @Override
    public void updateExchangeRates(ExchangeRates exchangeRates) {
        String sqlUpdateCurrency = "UPDATE ExchangeRate SET Rate = ? WHERE id = ?";

        try (Connection connection = SQLiteConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateCurrency)) {

            preparedStatement.setDouble(1, exchangeRates.getRate());
            preparedStatement.setInt(2, exchangeRates.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
    public Optional< ExchangeRates> getExchangeRatesByCode(String BaseCurrencyCode, String TargetCurrencyCode) {
        String query = "SELECT Id, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRate " +
                "WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?";

        try (Connection connection = SQLiteConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Optional<Currencies> baseCurrency = currenciesDAO.getCurrenciesByCode(BaseCurrencyCode);
            Optional<Currencies> targetCurrency = currenciesDAO.getCurrenciesByCode(TargetCurrencyCode);

            if (baseCurrency.isPresent() && targetCurrency.isPresent()) {
                Currencies baseCurrencyObj = baseCurrency.get();
                Currencies targetCurrencyObj = targetCurrency.get();

                preparedStatement.setInt(1, baseCurrencyObj.getId());
                preparedStatement.setInt(2, targetCurrencyObj.getId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        ExchangeRates exchangeRates = new ExchangeRates();
                        exchangeRates.setId(resultSet.getInt("Id"));
                        exchangeRates.setBaseCurrency(currenciesDAO.getCurrenciesById(resultSet.getInt("BaseCurrencyId")));
                        exchangeRates.setTargetCurrency(currenciesDAO.getCurrenciesById(resultSet.getInt("TargetCurrencyId")));
                        exchangeRates.setRate(resultSet.getDouble("Rate"));
                        return Optional.of(exchangeRates);
                    } else {
                        // Нет данных, возвращаем пустой Optional
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Database access error", e);
        }

        // Возвращаем пустой Optional, если валюта не найдена
        return Optional.empty();
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

        exchangeRates.setId(result.getInt("Id"));
        exchangeRates.setBaseCurrency(currenciesDAO.getCurrenciesById(result.getInt("BaseCurrencyId")));
        exchangeRates.setTargetCurrency(currenciesDAO.getCurrenciesById(result.getInt("TargetCurrencyId")));
        exchangeRates.setRate(result.getDouble("Rate"));

        return exchangeRates;
    }
}
