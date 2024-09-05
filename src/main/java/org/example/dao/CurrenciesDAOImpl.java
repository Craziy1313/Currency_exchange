package org.example.dao;

import org.example.models.Currencies;
import org.example.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CurrenciesDAOImpl implements CurrenciesDAO{

    @Override
    public void crateCurrenciesTable(String code, String fullName, String sign) {
        String sqlInsertCurrency = "INSERT INTO Currencies (code, fullname, sign) VALUES (?, ?, ?)";

        try (Connection connection = SQLiteConnection.getConnect();
             PreparedStatement pstmt = connection.prepareStatement(sqlInsertCurrency)) {

            pstmt.setString(1, code);
            pstmt.setString(2, fullName);
            pstmt.setString(3, sign);
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCurrenciesTable() {

    }

    @Override
    public void saveCurrencies() {

    }

    @Override
    public void updateCurrencies(Currencies currencies) {

    }

    @Override
    public Currencies getCurrencies(int id) {
        return null;
    }

    @Override
    public List<Currencies> getAllCurrencies() {
        return List.of();
    }
}
