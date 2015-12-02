/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsw.laberinto.controller;

import arsw.laberinto.model.game.*;
import java.util.*;
import org.springframework.stereotype.*;

/**
 *
 * @author pegasusmax
 */
@Service
public class ServicesFacade {

    private static final Mapa mapa = new Mapa("500", "300", "5", "4");

    public Mapa addPlayer(String name) throws Exception {
        mapa.asignarIdPlayer(name);
        mapa.tick();
        return mapa;
    }

    public List removePlayer(String name) throws Exception {
        mapa.sacarIdPlayer(name);
        mapa.tick();
        List lista = new ArrayList();
        lista.add(mapa.getRestriccionColored());
        lista.add(mapa.getJugadores());
        return lista;
    }

    public List movePlayer(String name, int keyCode, int speed) throws Exception {
        mapa.movimientoJugadores(name, keyCode, speed);
        mapa.tick();
        List lista = new ArrayList();
        lista.add(mapa.getRestriccionColored());
        lista.add(mapa.getJugadores());
        return lista;
    }
}
