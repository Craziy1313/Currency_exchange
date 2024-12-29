package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.CurrenciesDAOImpl;
import org.example.dto.ErrorMessage;
import org.example.models.Currencies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class CurrencyResponse {

    private final ObjectMapper mapper = new ObjectMapper();

    private final CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();

    private final ErrorResponse errorResponse = new ErrorResponse();

    public void sendCurrencyResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.trim().isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Код валюты отсутствует в адресе");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            errorResponse.sendErrorResponse(resp, errorMessage);
        } else {
            String currencyCode = pathInfo.substring(1);
            Optional<Currencies> currenciesOptional = currenciesDAO.getCurrenciesByCode(currencyCode);

            if (currenciesOptional.isPresent()) {
                Currencies currencies = currenciesOptional.get();
                String jsonResponse = mapper.writeValueAsString(currencies);

                PrintWriter out = resp.getWriter();
                out.print(jsonResponse);
                out.flush();
            } else {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setMessage("Валюта не найдена");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                errorResponse.sendErrorResponse(resp, errorMessage);
            }

        }
    }
}
