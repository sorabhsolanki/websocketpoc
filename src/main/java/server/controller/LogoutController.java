package server.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 */
public class LogoutController extends HttpServlet{

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession httpSession = request.getSession(false);

    if(httpSession != null){
      httpSession.invalidate();
    }

    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      cookie.setMaxAge(0);
      cookie.setValue(null);
      cookie.setPath("/");
      response.addCookie(cookie);
    }

    request.getRequestDispatcher("/index.html").forward(request, response);
  }

  }
