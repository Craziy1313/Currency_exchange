package org.example.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.ExchangeRatesDAOImpl;
import org.example.DTO.ErrorMessage;
import org.example.DTO.ExchangeCurrency;
import org.example.models.ExchangeRates;
import org.example.services.ExchangeCurrencyConvertor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.example.services.ErrorResponse.sendErrorResponse;

@WebServlet ("/exchange")
public class ExchangeServlets extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();

    private final ExchangeRatesDAOImpl exchangeRatesDAO = new ExchangeRatesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        Double amount = Double.valueOf(req.getParameter("amount"));

        Optional<ExchangeCurrency> exchangeCurrency = ExchangeCurrencyConvertor.convert(
                baseCurrencyCode, targetCurrencyCode, amount);
        if (exchangeCurrency.isPresent()) {
            ExchangeCurrency exchangeCurrencyObj = exchangeCurrency.get();

            String jsonResponse = mapper.writeValueAsString(exchangeCurrencyObj );

            PrintWriter out = resp.getWriter();
            out.println(jsonResponse);
            out.flush();
        } else {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Валюта не найдена");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            sendErrorResponse(resp, errorMessage);
        }
    }
}

