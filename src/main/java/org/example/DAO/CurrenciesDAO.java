package org.example.DAO;

import org.example.models.Currencies;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CurrenciesDAO {

    void crateCurrenciesTable();

    void deleteCurrenciesTable();

    void saveCurrencies(String code, String fullName, String sign) throws SQLException;

    void updateCurrencies(Currencies currencies);

    Optional <Currencies>  getCurrenciesByCode(String code);

    Currencies getCurrenciesById(int id);

    List<Currencies> getAllCurrencies();
}
