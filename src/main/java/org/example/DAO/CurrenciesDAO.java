package org.example.DAO;

import org.example.models.Currencies;

import java.util.List;

public interface CurrenciesDAO {

    void crateCurrenciesTable();

    void deleteCurrenciesTable();

    void saveCurrencies(String code, String fullName, String sign);

    void updateCurrencies(Currencies currencies);

    Currencies getCurrenciesByCode(String code);

    Currencies getCurrenciesById(int id);

    List<Currencies> getAllCurrencies();
}
