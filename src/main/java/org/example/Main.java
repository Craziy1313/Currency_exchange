package org.example;

import org.example.DAO.CurrenciesDAOImpl;
import org.example.DAO.ExchangeRatesDAO;
import org.example.DAO.ExchangeRatesDAOImpl;
import org.example.util.DatabaseInitializer;
import org.example.util.SQLiteConnection;

import java.sql.Connection;


public class Main {
    public static void main(String[] args) {

        ExchangeRatesDAOImpl dao = new ExchangeRatesDAOImpl();

        CurrenciesDAOImpl daoCurrencies = new CurrenciesDAOImpl();

        SQLiteConnection con = new SQLiteConnection();

        System.out.println(daoCurrencies.getAllCurrencies());
    }
}