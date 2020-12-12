package com.example.jupiter.servlet;

import com.example.jupiter.db.MySQLConnection;
import com.example.jupiter.db.MySQLException;
import com.example.jupiter.entity.LoginRequestBody;
import com.example.jupiter.entity.LoginResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        LoginRequestBody body = mapper.readValue(request.getReader(), LoginRequestBody.class);
        if (body == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String userName;
        MySQLConnection connection = null;
        try {
            connection = new MySQLConnection();
            String userId = body.getUserId();
            String password = ServletUtil.encryptPassword(body.getUserId(), body.getPassword());
            userName = connection.verifyLogin(userId, password);
        } catch (MySQLException e) {
            throw new ServletException(e);
        } finally {
            connection.close();
        }

        if (!userName.isEmpty()) {
            // Tomcat: relate session between request and response
            HttpSession session = request.getSession();
            session.setAttribute("user_id", body.getUserId());
            session.setAttribute("user_name", userName);
            // time to expire: 600s
            session.setMaxInactiveInterval(600);

            LoginResponseBody loginResponseBody = new LoginResponseBody(body.getUserId(), userName);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(loginResponseBody));

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


}
