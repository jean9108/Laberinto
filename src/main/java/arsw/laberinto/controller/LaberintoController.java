/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsw.laberinto.controller;

import arsw.laberinto.game.Mapa;
import arsw.laberinto.game.Player;
import arsw.laberinto.services.ServicesFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2086255
 */
@RestController
@RequestMapping("/laberinto")
public class LaberintoController {

    @Autowired
    ServicesFacade services;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check() {
        return "REST API OK";
    }

    @RequestMapping(value = "/mapa", method = RequestMethod.GET)
    public Mapa getMapa() {
        return services.getMapa();
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.GET)
    public List<Player> getPlayers() {
        return services.getMapa().getJugadores();
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public ResponseEntity<?> addPlayer(@RequestBody String name) throws Exception {
        services.addPlayer(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/quitar", method = RequestMethod.POST)
    public ResponseEntity<?> removePlayer(@RequestBody String name) throws Exception {
        services.removePlayer(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
