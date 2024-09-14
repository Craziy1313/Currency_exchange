package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.ExchangeRatesDAO;
import org.example.DAO.ExchangeRatesDAOImpl;
import org.example.models.ExchangeRates;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

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


    }
}
