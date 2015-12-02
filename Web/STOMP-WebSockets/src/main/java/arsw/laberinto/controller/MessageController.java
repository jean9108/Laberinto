package arsw.laberinto.controller;

import arsw.laberinto.model.game.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import arsw.laberinto.controller.messages.*;
import java.util.*;

@Controller
public class MessageController {

    private final Mapa mapa = new Mapa("500", "300", "5", "4");

    @MessageMapping("/map/player/connect")
    @SendTo("/topic/messages")
    public ServerMessage addPlayer(ClientName message) throws Exception {
        mapa.asignarIdPlayer(message.getMessage());
        mapa.tick();
        return new ServerMessage(mapa);
    }

    @MessageMapping("/map/player/disconnect")
    @SendTo("/topic/messages")
    public ServerMessage removePlayer(ClientName message) throws Exception {
        mapa.sacarIdPlayer(message.getMessage());
        mapa.tick();
        List lista = new ArrayList();
        lista.add(mapa.getRestriccionColored());
        lista.add(mapa.getJugadores());
        return new ServerMessage(lista);
    }

    @MessageMapping("/map/player/move")
    @SendTo("/topic/messages")
    public ServerMessage movePlayer(ClientMoving message) throws Exception {
        mapa.movimientoJugadores(message.getName(), message.getKeyCode(), message.getSpeed());
        mapa.tick();
        List lista = new ArrayList();
        lista.add(mapa.getRestriccionColored());
        lista.add(mapa.getJugadores());
        return new ServerMessage(lista);
    }
}
