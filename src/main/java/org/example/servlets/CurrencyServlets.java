package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.CurrenciesDAOImpl;
import org.example.DTO.ErrorMessage;
import org.example.models.Currencies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.example.services.ErrorResponse.sendErrorResponse;

@WebServlet ("/currency/*")
public class CurrencyServlets extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();
    private final CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.trim().isEmpty()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Код валюты отсутствует в адресе");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorResponse(resp, errorMessage);
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
                sendErrorResponse(resp, errorMessage);
            }

        }
    }
}
