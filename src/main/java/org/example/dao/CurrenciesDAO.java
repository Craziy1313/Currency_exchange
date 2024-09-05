package org.example.dao;

import org.example.models.Currencies;

import java.util.List;

public interface CurrenciesDAO {

    void crateCurrenciesTable(String code, String fullName, String sign);

    void deleteCurrenciesTable();

    void saveCurrencies();

    void updateCurrencies(Currencies currencies);

    Currencies getCurrencies(int id);

    List<Currencies> getAllCurrencies();
}
