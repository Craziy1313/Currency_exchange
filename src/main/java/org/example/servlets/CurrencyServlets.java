package org.example.servlets;

import org.example.services.CurrencyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet ("/currency/*")
public class CurrencyServlets extends HttpServlet {

    private final CurrencyResponse currencyResponse = new CurrencyResponse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        currencyResponse.sendCurrencyResponse(req,resp);

    }
}
