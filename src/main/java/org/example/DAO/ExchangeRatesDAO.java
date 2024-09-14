package org.example.DAO;

import org.example.models.ExchangeRates;

import java.util.List;

public interface ExchangeRatesDAO {

    void crateExchangeRatesTable();

    void deleteExchangeRatesTable();

    void saveExchangeRates(String BaseCurrencyId, String TargetCurrencyId, Double Rate);

    void updateExchangeRates(ExchangeRates exchangeRates);

    ExchangeRates getExchangeRates(int id);

    List<ExchangeRates> getAllExchangeRates();
}
