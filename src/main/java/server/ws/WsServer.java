package server.ws;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocketendpoint", configurator = MyConfigurator.class)
public class WsServer {

  private static Map<Session, String> sessionStringMap = new ConcurrentHashMap<Session, String>();

  @OnOpen
  public void onOpen(Session session){
    System.out.println("Connection opened.");

    List<String> completeString = (List<String>) session.getUserProperties().get("cookie");
    String name = completeString.get(0).substring(completeString.get(0).indexOf("=") + 1);
    sessionStringMap.put(session, name);
  }

  @OnClose
  public void onClose(Session session){
    System.out.println("Connection closed.");
    sessionStringMap.remove(session);
  }

  @OnMessage
  public void onMessage(Session session, final String message){
    System.out.println("Message from client : " + message);

    for(Entry<Session, String> entry : sessionStringMap.entrySet()){
         Session session1 = entry.getKey();
      try {
        session1.getBasicRemote().sendText(sessionStringMap.get(session) + ":" + message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @OnError
  public void onError(Throwable e){
    System.out.println(e.getMessage());

  }

}
