/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsw.laberinto.services;

import arsw.laberinto.game.Mapa;
import arsw.laberinto.game.Player;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2086255
 */
@Service
public class ServicesFacade {

    private static final Mapa mapa = new Mapa("500", "300", "5", "4");

    public Mapa getMapa() {
        return mapa;
    }

    public void addPlayer(String name) throws Exception {
        mapa.asignarIdPlayer(name);
    }

    public void removePlayer(String name) {
        mapa.sacarIdPlayer(name);
    }

    public List<Player> getPlayers() {
        return mapa.getJugadores();
    }

    public Player getPlayerByName(String name) {
        Player resp = null;
        for (Player p : mapa.getJugadores()) {
            if (p.getId().equals(name)) {
                resp = p;
                break;
            }
        }
        return resp;
    }

    public void movePlayer(String pl, int sel, int esp) {
        mapa.movimientoJugadores(pl, sel, esp);
    }
}
