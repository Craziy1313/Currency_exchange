package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.CurrenciesDAOImpl;
import org.example.DTO.ErrorMessage;
import org.example.models.Currencies;
import org.example.services.ParameterValidation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.example.services.ErrorResponse.sendErrorResponse;

@WebServlet("/currencies")
public class CurrenciesServlets extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();
    private final CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Currencies> currencies = currenciesDAO.getAllCurrencies();


        String jsonResponse = mapper.writeValueAsString(currencies);

        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        Optional <ErrorMessage> errorMessageOptional = ParameterValidation.CurrenciesValidation(name, code, sign);

        if (errorMessageOptional.isPresent()) {
            ErrorMessage errorMessage = errorMessageOptional.get();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400 Ошибка
            sendErrorResponse(resp, errorMessage);

        } else {

            try {
                currenciesDAO.saveCurrencies(code, name, sign);
                Optional<Currencies> currenciesOptional = currenciesDAO.getCurrenciesByCode(code);
                if (currenciesOptional.isPresent()) {
                    Currencies currencies = currenciesOptional.get();
                    String jsonResponse = mapper.writeValueAsString(currencies);

                    resp.setStatus(HttpServletResponse.SC_CREATED); //201
                    PrintWriter out = resp.getWriter();
                    out.println(jsonResponse);
                    out.flush();
                }

            } catch (SQLException e) {
                ErrorMessage errorMessage = new ErrorMessage();

                if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT);
                    errorMessage.setMessage("Код валюты: '" + code + "' уже внесен в базу данных"); // 409 Конфликт
                    sendErrorResponse(resp,errorMessage);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    errorMessage.setMessage(e.getMessage());// 500 Ошибка сервера
                    sendErrorResponse(resp, errorMessage);
                }
            }
        }
    }
}
