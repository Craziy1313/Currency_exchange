package org.example.util;

import org.example.DAO.CurrenciesDAOImpl;
import org.example.DAO.ExchangeRatesDAOImpl;

public class DatabaseInitializer {

    static CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();
    static ExchangeRatesDAOImpl exchangeRateDAO = new ExchangeRatesDAOImpl();

    public static void tableEnrich() {

        currenciesDAO.saveCurrencies("USD", "US Dollar", "$");
        currenciesDAO.saveCurrencies("EUR", "Euro", "€");
        currenciesDAO.saveCurrencies("RUB", "Russian Ruble", "₽");
        currenciesDAO.saveCurrencies("UAH", "Hryvnia", "₴");
        currenciesDAO.saveCurrencies("KZT", "Tenge", "₸");
        currenciesDAO.saveCurrencies("GBP", "Pound Sterling", "£");
        currenciesDAO.saveCurrencies("AUD", "Australian dollar", "A€");

        exchangeRateDAO.saveExchangeRates(1, 2,0.94);
        exchangeRateDAO.saveExchangeRates(1, 3, 63.75);
        exchangeRateDAO.saveExchangeRates(1, 4, 36.95);
        exchangeRateDAO.saveExchangeRates(1, 5, 469.88);
        exchangeRateDAO.saveExchangeRates(1, 6, 0.81);
    }

    public static void initializeDatabase() {
        currenciesDAO.crateCurrenciesTable();
        exchangeRateDAO.crateExchangeRatesTable();
        tableEnrich();

    }
}
