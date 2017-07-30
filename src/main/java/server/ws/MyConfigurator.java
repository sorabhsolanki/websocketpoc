package server.ws;

import java.util.List;
import java.util.Map;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 */
public class MyConfigurator extends Configurator {

  @Override
  public void modifyHandshake(ServerEndpointConfig config,
      HandshakeRequest request,
      HandshakeResponse response){

    Map<String,List<String>> headers = request.getHeaders();
    config.getUserProperties().put("cookie",headers.get("cookie"));
  }
}
