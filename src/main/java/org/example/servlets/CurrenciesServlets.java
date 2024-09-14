package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAO.CurrenciesDAOImpl;
import org.example.models.Currencies;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Currencies> currencies = currenciesDAO.getAllCurrencies();
        if (currencies == null) {
            logger.warning("Currencies list is null.");
        }

        ObjectMapper mapper = new ObjectMapper();
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

        currenciesDAO.saveCurrencies(code, name, sign);
        System.out.println("Saving currency: code=" + code + ", name=" + name + ", sign=" + sign);

        Currencies currencies = currenciesDAO.getCurrenciesByCode(code);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(currencies);

        PrintWriter out = resp.getWriter();
        out.println(jsonResponse);
        out.flush();

    }
}
