package server.connection;

import java.sql.Connection;

/**
 */
public class Conn {

  private final Connection connection;
  private final int index;
  private boolean free;

  public Conn(Connection connection, int index, boolean free) {
    this.connection = connection;
    this.index = index;
    this.free = free;
  }

  public Connection getConnection() {
    return connection;
  }

  public int getIndex() {
    return index;
  }

  public boolean isFree() {
    return free;
  }

  public void setFree(boolean free) {
    this.free = free;
  }
}
