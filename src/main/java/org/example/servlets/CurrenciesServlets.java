package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.CurrenciesDAOImpl;
import org.example.models.Currencies;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/currencies")
public class CurrenciesServlets extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CurrenciesServlets.class.getName());
    private final CurrenciesDAOImpl currenciesDAO = new CurrenciesDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Установка типа контента
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Получение списка валют из базы данных
        List<Currencies> currencies = currenciesDAO.getAllCurrencies();
        if (currencies == null) {
            logger.warning("Currencies list is null.");
        }

        // Преобразование списка валют в JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(currencies);

        // Отправка JSON ответа
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if ("application/json".equals(req.getContentType())) {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;

            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            String jsonString = jsonBuffer.toString();

            ObjectMapper mapper = new ObjectMapper();
            Currencies currencies = mapper.readValue(jsonString, Currencies.class);

            currenciesDAO.saveCurrencies(currencies.getCode(), currencies.getFullName(), currencies.getSign());
        }
    }
}
