package org.example.dao;

import org.example.models.Currencies;

import java.util.List;

public interface ExchangeRatesDAO {

    void crateExchangeRatesTable();

    void deleteExchangeRatesTable();

    void saveExchangeRates();

    void updateExchangeRates(Currencies currencies);

    Currencies getExchangeRates(int id);

    List<Currencies> getAllExchangeRates();
}
