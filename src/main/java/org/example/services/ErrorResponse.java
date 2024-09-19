package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.ErrorMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorResponse {

    public static void sendErrorResponse(HttpServletResponse resp, ErrorMessage errorMessage) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(errorMessage);

        PrintWriter out = resp.getWriter();
        out.println(jsonResponse);
        out.flush();
    }
}
