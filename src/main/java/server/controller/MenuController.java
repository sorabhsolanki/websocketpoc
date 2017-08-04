package server.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class MenuController extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    if (request.getParameter("actionperformed").equals("chat")) {
      request.getRequestDispatcher("/chat.html").forward(request, response);
    } else if (request.getParameter("actionperformed").equals("tournament")) {
      request.getRequestDispatcher("/tournament.jsp").forward(request, response);
    }else
      request.getRequestDispatcher("/errorPage.html").forward(request, response);
  }

}
