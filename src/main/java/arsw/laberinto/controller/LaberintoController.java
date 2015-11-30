package arsw.laberinto.controller;

import arsw.laberinto.model.game.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author pegasusmax
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

    @RequestMapping(value = "/player/{name}/connect", method = RequestMethod.GET)
    public Mapa addPlayer(@PathVariable("name") String name) throws Exception {
        return services.addPlayer(name);
    }

    @RequestMapping(value = "/player/{name}/disconnect", method = RequestMethod.GET)
    public List removePlayer(@PathVariable("name") String name) throws Exception {
        return services.removePlayer(name);
    }

    @RequestMapping(value = "/player/{name}/move/{key}/speed/{speed}", method = RequestMethod.POST)
    public List movePlayer(@PathVariable("name") String name, @PathVariable("key") int key, @PathVariable("speed") int speed) throws Exception {
        return services.movePlayer(name, key, speed);
    }
}
