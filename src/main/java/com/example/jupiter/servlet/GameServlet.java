package com.example.jupiter.servlet;

import com.example.jupiter.entity.Game;
import com.example.jupiter.external.TwitchClient;
import com.example.jupiter.external.TwitchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//localhost:8080/jupiter/game
@WebServlet(name = "GameServlet", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameName = request.getParameter("game_name");
        TwitchClient client = new TwitchClient();

        response.setContentType("application/json;charset=UTF-8");
        try {
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }
    }

}
