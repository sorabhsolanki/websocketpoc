package server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import server.connection.Conn;
import server.connection.ConnectionManager;
import server.dto.Tournament;

/**
 */
public class TournamentRepository {


  private final ConnectionManager connectionManager;
  private static final String create_tournament =
      " INSERT INTO tournament (id, name, admin_name, created_at, is_active)"
          + " VALUES (?, ?, ?, ?, ?)";

  private static final String select_user_ids = "select id from user where user_name in";

  private static final String insert_user_tournament = "insert into user_tournament(user_id, tournament_id) "
      + "values (?, ?)";

  private static final String select_tournament = "select t.name, t.admin_name, t.created_at, t.is_active"
      + " from tournament t, user_tournament ut, "
      + "user u where u.user_name = ? and u.id = ut.user_id and ut.tournament_id = t.id";


  public TournamentRepository(ConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  public void persist(Tournament tournament, String[] invitees) {

    Conn conn = connectionManager.getConnection();
    try {
      Connection connection = conn.getConnection();
      PreparedStatement preparedStmt = connection.prepareStatement(create_tournament);
      preparedStmt.setString(1, tournament.getId());
      preparedStmt.setString(2, tournament.getName());
      preparedStmt.setString(3, tournament.getAdminName());
      preparedStmt.setTimestamp(4, new Timestamp(tournament.getCreatedAt().getTime()));
      preparedStmt.setBoolean(5, tournament.isActive());

      preparedStmt.execute();

      List<String> userIds = getUserIds(connection, invitees);

      PreparedStatement stmt = connection.prepareStatement(insert_user_tournament);
      for(String userId : userIds){
        stmt.setString(1, userId);
        stmt.setString(2, tournament.getId());
        stmt.execute();
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionManager.closeConnection(conn);
    }
  }

  private List<String> getUserIds(Connection connection, String[] invitees) {

    List<String> userIds = new ArrayList<>(invitees.length);
    try {
      StringBuilder parameterBuilder = new StringBuilder();
      parameterBuilder.append(" (");
      for (int i = 0; i < invitees.length; i++) {
        parameterBuilder.append("?");
        if (invitees.length > i + 1) {
          parameterBuilder.append(",");
        }
      }
      parameterBuilder.append(")");

      PreparedStatement statement = null;

      statement = connection.prepareStatement(select_user_ids + parameterBuilder);

      for (int i = 1; i < invitees.length + 1; i++) {
        statement.setString(i, invitees[i - 1]);
      }

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        userIds.add(resultSet.getString("id"));
      }

      } catch (SQLException e) {
      System.out.println(e.getMessage());
    }


    return userIds;
  }

  public List<Tournament> getAccessDetails(String userName) {

    Conn conn = connectionManager.getConnection();
    List<Tournament> list = new LinkedList<>();
    try {
      Connection connection = conn.getConnection();
      PreparedStatement preparedStmt = connection.prepareStatement(select_tournament);
      preparedStmt.setString(1, userName);
      ResultSet resultSet = preparedStmt.executeQuery();
      while (resultSet.next()) {

        Tournament tournament = new Tournament();
        tournament.setName(resultSet.getString("name"));
        tournament.setAdminName(resultSet.getString("admin_name"));
        tournament.setCreatedAt(resultSet.getDate("created_at"));
        tournament.setActive(resultSet.getBoolean("is_active"));

        list.add(tournament);
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionManager.closeConnection(conn);
    }

    return list;
  }
}
