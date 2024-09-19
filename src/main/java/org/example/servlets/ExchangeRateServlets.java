package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static org.example.services.ErrorResponse.sendErrorResponse;

@WebServlet ("/exchangeRate/*")
public class ExchangeRateServlets extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ExchangeRatesDAOImpl exchangeRatesDAO = new ExchangeRatesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo().substring(1);

        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3, 6);

        Optional < ErrorMessage> errorMessageOptional = ParameterValidation.ExchangeRateValidation(
                baseCurrencyCode, targetCurrencyCode);

        if (errorMessageOptional.isPresent()) {
            ErrorMessage errorMessage = errorMessageOptional.get();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400 Ошибка
            sendErrorResponse(resp, errorMessage);
        } else {
            Optional<ExchangeRates> exchangeRates = exchangeRatesDAO.getExchangeRatesByCode(
                    baseCurrencyCode, targetCurrencyCode);
            if (exchangeRates.isPresent()) {
                String jsonResponse = mapper.writeValueAsString(exchangeRates.get());

                PrintWriter out = resp.getWriter();
                out.print(jsonResponse);
                out.flush();
            } else {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setMessage("Обменный курс для пары не найден");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                sendErrorResponse(resp, errorMessage);
            }
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo().substring(1);

        String baseCurrencyCode = pathInfo.substring(0, 3);
        String targetCurrencyCode = pathInfo.substring(3, 6);

        String parameter = req.getReader().readLine();
        String paramRateValue = parameter.replace("rate=", "");
        Double rate = Double.valueOf(paramRateValue);

        Optional <ErrorMessage> errorMessageOptional = ParameterValidation.ExchangeRatesValidation(
                baseCurrencyCode, targetCurrencyCode, rate);

        if (errorMessageOptional.isPresent()) {
            ErrorMessage errorMessage = errorMessageOptional.get();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400 Ошибка
            sendErrorResponse(resp, errorMessage);
        } else {

            Optional<ExchangeRates> exchangeRates = exchangeRatesDAO.getExchangeRatesByCode(
                    baseCurrencyCode, targetCurrencyCode);

            if (exchangeRates.isPresent()) {
                exchangeRates.get().setRate(rate);
                System.out.println(exchangeRates.get());
                exchangeRatesDAO.updateExchangeRates(exchangeRates.get());

                String jsonResponse = mapper.writeValueAsString(exchangeRates.get());
                PrintWriter out = resp.getWriter();
                out.print(jsonResponse);
                out.flush();
            } else {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setMessage("Валютная пара отсутствует в базе данных");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                sendErrorResponse(resp, errorMessage);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }
}
