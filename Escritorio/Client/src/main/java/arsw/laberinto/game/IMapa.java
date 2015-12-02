package arsw.laberinto.game;

import java.util.*;

public interface IMapa {

    public void movimientoJugadores(String player, int keyCode, int i);

    public void tick();

    public ArrayList<Integer> getJugadoresX();

    public ArrayList<Integer> getJugadoresY();

    public ArrayList<String> getJugadoresC();

    public Integer getJugadoresW();

    public Integer getJugadoresH();

    public ArrayList<Integer> getRestriccionX();

    public ArrayList<Integer> getRestriccionY();

    public ArrayList<String> getRestriccionC();

    public Integer getRestriccionW();

    public Integer getRestriccionH();

    public String getMeta();

    public String getIP();

    public void asignarIdPlayer(String ids) throws Exception;
    
    public void sacarIdPlayer(String ids);
}
