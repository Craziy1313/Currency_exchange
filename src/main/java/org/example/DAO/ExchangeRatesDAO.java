package org.example.DAO;

import org.example.models.ExchangeRates;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ExchangeRatesDAO {

    void crateExchangeRatesTable();

    void deleteExchangeRatesTable();

    void saveExchangeRates(String BaseCurrencyId, String TargetCurrencyId, Double Rate) throws SQLException;

    void updateExchangeRates(ExchangeRates exchangeRates);

    ExchangeRates getExchangeRates(int id);

    Optional <ExchangeRates> getExchangeRatesByCode(String BaseCurrencyCode, String TargetCurrencyCode);

    List<ExchangeRates> getAllExchangeRates();
}
