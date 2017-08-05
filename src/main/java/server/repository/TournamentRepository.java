package server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

      String[] userIds = getUserIds(connection, invitees);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      connectionManager.closeConnection(conn);
    }
  }

  private String[] getUserIds(Connection connection, String[] invitees) {

    String[] userNames = new String[invitees.length];
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

      }

      } catch (SQLException e) {
      System.out.println(e.getMessage());
    }


    return userNames;
  }

}
