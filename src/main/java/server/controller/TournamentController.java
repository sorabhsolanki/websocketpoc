package server.controller;

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.dto.Tournament;
import server.repository.TournamentRepository;
import server.service.TournamentService;

/**
 */
public class TournamentController extends HttpServlet {


  private TournamentService tournamentService = new TournamentService(new TournamentRepository());

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String nameOfTournament = request.getParameter("name");
    String invitedUsers = request.getParameter("invite");

    StringTokenizer stringTokenizer = new StringTokenizer(invitedUsers, ",");
    String[] invitees = new String[stringTokenizer.countTokens()];
    int count = 0;
    while (stringTokenizer.hasMoreTokens()) {
      if (isValidUserName(stringTokenizer.nextToken().trim())) {
        invitees[count++] = stringTokenizer.nextToken().trim();
      }
      else{
        request.getRequestDispatcher("/errorPage.html").forward(request, response);
        return;
      }
    }

    String nameOfAdmin = null;

    Cookie [] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if ("name".equals(cookie.getName())) {
        nameOfAdmin = cookie.getValue();
      }
    }

    saveTournament(nameOfAdmin, nameOfTournament, invitees);

    request.setAttribute("finalParam", "tournament created successfully.");

    request.getRequestDispatcher("/tournament.jsp").forward(request, response);

  }

  private void saveTournament(String nameOfAdmin, String nameOfTournament,
      String[] invitees) {
    Tournament tournament = new Tournament(nameOfTournament, nameOfAdmin, true);
    tournamentService.saveTournament(tournament);
  }


  private boolean isValidUserName(String usrName) {
    for (UserEnum userEnum : UserEnum.values()) {
      if (usrName.toLowerCase().equals(userEnum.getName())) {
        return true;
      }
    }

    return false;
  }

  private enum UserEnum {
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
