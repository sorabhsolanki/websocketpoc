package server.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.connection.ConnectionManager;
import server.dto.Tournament;
import server.repository.TournamentRepository;
import server.service.TournamentService;

/**
 */
public class LoginController extends HttpServlet{

  private static final ConnectionManager connectionManager = ConnectionManager.getInstance(20);
  private static TournamentService tournamentService;

  @Override
  public void init(){
    TournamentService.init(new TournamentRepository(connectionManager));
    tournamentService = TournamentService.getInstance();
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String userName = request.getParameter("usrName");
    System.out.println("User : " + userName);

    if(!isValidUserName(userName)) {
      request.getRequestDispatcher("/errorPage.html").forward(request, response);
      return;
    }

    Cookie cookie = new Cookie("name", userName);
    response.addCookie(cookie);

    List<Tournament> tournaments = tournamentService.getTournamentAccessDetails(userName);
    request.setAttribute("tournamentlist", tournaments);
    request.getRequestDispatcher("/menu.jsp").forward(request, response);
  }


  private boolean isValidUserName(String usrName){
    for (UserEnum userEnum : UserEnum.values()) {
      if(usrName.toLowerCase().equals(userEnum.getName()))
        return true;
    }

    return false;
  }

  private enum UserEnum{
    Diwakar("diwakar"),
    Sorabh("sorabh"),
    Rachit("rachit"),
    Akhilesh("akhilesh"),
    Parul("parul");

    private String name;

    UserEnum(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

}
