package server.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ConnectionManager {

  private static volatile ConnectionManager MANAGER;
  private final int SIZE;

  private final Conn[] conns;

  private int count = 0;
  private final Lock lock = new ReentrantLock();
  private final Condition empty = lock.newCondition();

  private ConnectionManager(int size) {
    SIZE = size;
    conns = new Conn[size];
    initConnections();
  }

  public static ConnectionManager getInstance(int size) {
    if (MANAGER == null) {
      synchronized (ConnectionManager.class) {
        if (MANAGER == null) {
          MANAGER = new ConnectionManager(size);
        }
      }
    }
    return MANAGER;
  }


  private void initConnections() {
    try {
      Class.forName("com.mysql.jdbc.Driver");

      for(int i = 0; i < SIZE; i++)
        conns[i] = new Conn(DriverManager.getConnection("jdbc:mysql://localhost:3306/chatroom",
            "scoreit", "scoreit"), i, true);
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }


  public Conn getConnection(){
    Conn conn = null;
    try{
      lock.lock();
      while(count == SIZE) {
        empty.await();
      }

      for(int i = 0; i < SIZE; i++){
        if(conns[i].isFree()){
          conn = conns[i];
          conns[i].setFree(false);
          break;
        }
      }
      count ++;
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    } finally {
      lock.unlock();
    }
    return conn;
  }

  public void closeConnection(Conn conn){
    try{
      lock.lock();
      conns[conn.getIndex()].setFree(true);
      count --;
      empty.signalAll();
    }finally {
      lock.unlock();
    }
  }

  public void finalClose(){
    for(int i = 0; i < SIZE; i++){
      try {
        conns[i].getConnection().close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /*public static void main(String[] args) {

    ConnectionManager connectionManager = ConnectionManager.getInstance(12);

    Conn[] conns = new Conn[12];

    for(int i = 0 ; i < 12; i++){
      conns[i] = connectionManager.getConnection();
    }


    for(int i = 0 ; i < 12; i++)
      connectionManager.closeConnection(conns[i]);
  }*/

}
