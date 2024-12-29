package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ErrorMessage;
import org.example.dto.ExchangeCurrency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class ExchangeResponse {

    private final ExchangeCurrencyConvertor exchangeCurrencyConvertor = new ExchangeCurrencyConvertor();

    private final ObjectMapper mapper = new ObjectMapper();

    private final ErrorResponse errorResponse = new ErrorResponse();

    public void sendExchangeResponce (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        Double amount = Double.valueOf(req.getParameter("amount"));

        Optional<ExchangeCurrency> exchangeCurrency = exchangeCurrencyConvertor.convert(
                baseCurrencyCode, targetCurrencyCode, amount);
        if (exchangeCurrency.isPresent()) {
            ExchangeCurrency exchangeCurrencyObj = exchangeCurrency.get();

            String jsonResponse = mapper.writeValueAsString(exchangeCurrencyObj);

            PrintWriter out = resp.getWriter();
            out.println(jsonResponse);
            out.flush();
        } else {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Валюта не найдена");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            errorResponse.sendErrorResponse(resp, errorMessage);
        }
    }
}
