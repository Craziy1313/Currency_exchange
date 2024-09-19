package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.ExchangeRatesDAO;
import org.example.DAO.ExchangeRatesDAOImpl;
import org.example.DTO.ErrorMessage;
import org.example.models.ExchangeRates;
import org.example.services.ParameterValidation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.example.services.ErrorResponse.sendErrorResponse;

@WebServlet ("/exchangeRates")
public class ExchangeRatesServlets extends HttpServlet {

    private final ExchangeRatesDAO exchangeRatesDAO = new ExchangeRatesDAOImpl();
    private static final Logger logger = Logger.getLogger(ExchangeRatesServlets.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<ExchangeRates> exchangeRates = exchangeRatesDAO.getAllExchangeRates();
        if (exchangeRates == null) {
            logger.warning("exchangeRates list is null.");
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(exchangeRates);

        PrintWriter out = resp.getWriter();
        out.println(jsonResponse);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        Double rate = Double.valueOf(req.getParameter("rate"));

        Optional <ErrorMessage> errorMessageOptional = ParameterValidation.ExchangeRatesValidation(
                baseCurrencyCode, targetCurrencyCode, rate);

        if (errorMessageOptional.isPresent()) {
            ErrorMessage errorMessage = errorMessageOptional.get();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400 Ошибка
            sendErrorResponse(resp, errorMessage);
        } else {
            try {
                exchangeRatesDAO.saveExchangeRates(baseCurrencyCode, targetCurrencyCode, rate);
                Optional<ExchangeRates> exchangeRates = exchangeRatesDAO.getExchangeRatesByCode(baseCurrencyCode, targetCurrencyCode);
                if (exchangeRates.isPresent()) {
                    ExchangeRates exchangeRate = exchangeRates.get();
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonResponse = mapper.writeValueAsString(exchangeRate);

                    PrintWriter out = resp.getWriter();
                    out.println(jsonResponse);
                    out.flush();
                } else {
                    ErrorMessage errorMessage =  new ErrorMessage();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    errorMessage.setMessage("Одна (или обе) валюта из валютной пары не существует в БД"); // 409 Конфликт
                    sendErrorResponse(resp,errorMessage);
                }

            } catch (SQLException e) {
                ErrorMessage errorMessage = new ErrorMessage();

                if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE") ||
                        e.getMessage().contains("Валютная пара с таким кодом уже существует")) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    errorMessage.setMessage("Валютная пара с таким кодом уже существует"); // 409 Конфликт
                    sendErrorResponse(resp, errorMessage);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    errorMessage.setMessage(e.getMessage());// 500 Ошибка сервера
                    sendErrorResponse(resp, errorMessage);
                }
            }
        }
    }
}
