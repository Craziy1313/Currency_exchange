package org.example.servlets;


import org.example.services.ExchangeResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet ("/exchange")
public class ExchangeServlets extends HttpServlet {

    private final ExchangeResponse exchangeResponce = new ExchangeResponse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        exchangeResponce.sendExchangeResponce(req, resp);
    }

}

