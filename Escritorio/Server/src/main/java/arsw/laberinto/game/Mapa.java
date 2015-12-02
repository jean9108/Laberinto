package arsw.laberinto.game;

import java.net.*;
import java.util.*;
import java.util.logging.*;
import arsw.laberinto.utils.*;
import arsw.laberinto.importer.*;
import java.awt.event.*;

public class Mapa implements IMapa {

    private static final String WINNERS = "Ganadores del mapa";
    private static final String NO = "No";
    private static final String CYAN = "CYAN";
    private static final String ORANGE = "ORANGE";
    private static final String GREEN = "GREEN";
    private static final String RED = "RED";
    private static final String WHITE = "WHITE";
    private static final String BLUE = "BLUE";
    private static final char WALL = '*';
    private static final char ELEMENT = '#';
    private static final char ONE = '1';
    private static final char TWO = '2';
    private static final char THREE = '3';
    private static final char FOUR = '4';
    private static int WIDTH;
    private static int HEIGHT;
    private static int SCALE;
    private final ArrayList<Element> restriccion;
    ArrayList<Player> jugadores = new ArrayList<>();
    ArrayList<String> colores = new ArrayList<>();
    private String meta = NO;
    private static int numGamers;

    public Mapa(String w, String h, String s, String j) {
        WIDTH = Integer.parseInt(w);
        HEIGHT = Integer.parseInt(h);
        SCALE = Integer.parseInt(s);
        numGamers = Integer.parseInt(j);
        hacerJugadores();
        if (Integer.parseInt(j) != 4) {
            switch (Integer.parseInt(j)) {
                case 1:
                    jugadores.remove(1);
                    jugadores.remove(1);
                    jugadores.remove(1);
                    break;
                case 2:
                    jugadores.remove(1);
                    jugadores.remove(1);
                    break;
                case 3:
                    jugadores.remove(1);
                    break;
                default:
                    break;
            }
        }
        restriccion = lectura(Integer.parseInt(w), Integer.parseInt(h), Integer.parseInt(s));

    }

    private void hacerJugadores() {
        jugadores.add(new Player(85, 490, WIDTH, HEIGHT, SCALE, 10, 10, CYAN));
        jugadores.add(new Player(195, 490, WIDTH, HEIGHT, SCALE, 10, 10, ORANGE));
        jugadores.add(new Player(305, 490, WIDTH, HEIGHT, SCALE, 10, 10, GREEN));
        jugadores.add(new Player(415, 490, WIDTH, HEIGHT, SCALE, 10, 10, RED));
    }

    @Override
    public synchronized void asignarIdPlayer(String ids) throws Exception {
        boolean found = false;
        for (Player d : getJugadores()) {
            if (d.getId() == null) {
                d.setId(ids);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Partida completa.");
        }
    }

    @Override
    public synchronized void sacarIdPlayer(String ids) {
        for (Player d : getJugadores()) {
            if (d.getId().equals(ids)) {
                d.setId(null);
                break;
            }
        }
    }

    private static int getNumGamers() {
        return numGamers;
    }

    public ArrayList<Player> getJugadores() {
        return jugadores;
    }

    @Override
    public synchronized ArrayList<Integer> getJugadoresX() {
        ArrayList<Integer> qdf = new ArrayList<>();
        for (Player we : getJugadores()) {
            qdf.add(we.getX());
        }
        return qdf;
    }

    @Override
    public synchronized ArrayList<Integer> getJugadoresY() {
        ArrayList<Integer> qdf = new ArrayList<>();
        for (Player we : getJugadores()) {
            qdf.add(we.getY());
        }
        return qdf;
    }

    @Override
    public synchronized ArrayList<String> getJugadoresC() {
        ArrayList<String> qdf = new ArrayList<>();
        for (Player we : getJugadores()) {
            qdf.add(we.getCol());
        }
        return qdf;
    }

    @Override
    public synchronized Integer getJugadoresW() {
        if (!getJugadores().isEmpty()) {
            return getJugadores().get(0).getWidth();
        } else {
            return 10;
        }
    }

    @Override
    public synchronized Integer getJugadoresH() {
        if (!getJugadores().isEmpty()) {
            return getJugadores().get(0).getHeight();
        } else {
            return 10;
        }
    }

    public ArrayList<Element> getRestriccion() {
        return restriccion;
    }

    @Override
    public synchronized ArrayList<Integer> getRestriccionX() {
        ArrayList<Integer> qdf = new ArrayList<>();
        for (Element we : getRestriccion()) {
            qdf.add(we.getX());
        }
        return qdf;
    }

    @Override
    public synchronized ArrayList<Integer> getRestriccionY() {
        ArrayList<Integer> qdf = new ArrayList<>();
        for (Element we : getRestriccion()) {
            qdf.add(we.getY());
        }
        return qdf;
    }

    @Override
    public synchronized ArrayList<String> getRestriccionC() {
        ArrayList<String> qdf = new ArrayList<>();
        for (Element we : getRestriccion()) {
            qdf.add(we.getCol());
        }
        return qdf;
    }

    @Override
    public synchronized Integer getRestriccionW() {
        if (!getRestriccion().isEmpty()) {
            return getRestriccion().get(0).getWidth();
        } else {
            return 10;
        }
    }

    @Override
    public synchronized Integer getRestriccionH() {
        if (!getRestriccion().isEmpty()) {
            return getRestriccion().get(0).getHeight();
        } else {
            return 10;
        }
    }

    private ArrayList<Element> lectura(int width, int height, int scale) {
        int wh = 5;
        ArrayList<String> map = MapImporter.leer(getNumGamers());
        ArrayList<Element> wall = new ArrayList<>();
        for (int i = 0, w = 5, h = 5; i < map.size() && w < width * scale && h < height * scale; i++) {
            for (int j = 0; j < map.get(i).length() && w < width * scale && h < height * scale; j++) {
                char sd = map.get(i).charAt(j);

                switch (sd) {
                    case WALL:
                        wall.add(new Element(w, h, wh, wh, WHITE));
                        break;
                    case ONE:
                        if (getNumGamers() >= 1) {
                            wall.add(new Element(w, h, wh, wh, getJugadores().get(0).getCol()));
                        }
                        break;
                    case TWO:
                        if (getNumGamers() >= 2) {
                            wall.add(new Element(w, h, wh, wh, getJugadores().get(1).getCol()));
                        }
                        break;
                    case THREE:
                        if (getNumGamers() >= 3) {
                            wall.add(new Element(w, h, wh, wh, getJugadores().get(2).getCol()));
                        }
                        break;
                    case FOUR:
                        if (getNumGamers() >= 4) {
                            wall.add(new Element(w, h, wh, wh, getJugadores().get(3).getCol()));
                        }
                        break;
                    case ELEMENT:
                        wall.add(new Element(w, h, wh, wh, BLUE));
                        break;
                    default:
                        break;
                }
                w = w + 10;
            }
            h = h + 10;
            w = 5;
        }
        return wall;
    }

    @Override
    public void movimientoJugadores(String pl, int sel, int esp) {
        Player fi = getPlayerId(pl);
        switch (sel) {
            case KeyEvent.VK_RIGHT://39:
                fi.setVelX(esp);//Flecha derecha
                break;

            case KeyEvent.VK_LEFT://37:
                fi.setVelX(-esp);//Flecha izquierda
                break;

            case KeyEvent.VK_UP://38:
                fi.setVelY(-esp);//Flecha arriba
                break;

            case KeyEvent.VK_DOWN://40:
                fi.setVelY(esp);//Flecha abajo
                break;
        }
    }

    private Player getPlayerId(String wd) {
        for (Player y : getJugadores()) {
            if (y.getId() != null) {
                if (y.getId().equals(wd)) {
                    return y;
                }
            }
        }
        return null;
    }

    @Override
    public synchronized String getMeta() {
        return meta;
    }

    @Override
    public synchronized void tick() {
        ArrayList<Element> d = new ArrayList<>();
        for (Player l : jugadores) {
            l.tick();
        }
        for (Player z : jugadores) {
            for (Element x : restriccion) {
                // To avoid player go outside the arena.
                tickUp(z, x, d);
                tickDown(z, x, d);
                tickRight(z, x, d);
                tickLeft(z, x, d);

            }
        }
        restriccion.removeAll(d);
    }

    private void tickRight(Player z, Element x, ArrayList<Element> d) {
        if ((z.getX() - (x.getX() - 15)) > 0 && (z.getX() - (x.getX() - 15)) < 15
                && (z.getY() - x.getY()) < 10 && (z.getY() - x.getY()) > -5) {
            if (z.getCol().equals(x.getCol())) {
                d.add(x);
            } else if (x.getCol().equals(BLUE)) {
                if (meta.equals(NO)) {
                    meta = WINNERS;
                } else {
                    d.add(x);
                }
            } else {
                z.setX(z.getX() - (z.getX() - (x.getX() - 15)));
            }

        }
    }

    private void tickLeft(Player z, Element x, ArrayList<Element> d) {
        if ((z.getX() - (x.getX() + 15)) < 0 && (z.getX() - (x.getX() + 15)) > -15
                && (z.getY() - x.getY()) < 10 && (z.getY() - x.getY()) > -5) {
            if (z.getCol().equals(x.getCol())) {
                d.add(x);
            } else if (x.getCol().equals(BLUE)) {
                if (meta.equals(NO)) {
                    meta = WINNERS;
                } else {
                    d.add(x);
                }
            } else {
                z.setX(z.getX() + ((x.getX() + 15) - z.getX()));
            }

        }
    }

    private void tickUp(Player z, Element x, ArrayList<Element> d) {
        if ((z.getY() - (x.getY() + 15)) < 0 && (z.getY() - (x.getY() + 15)) > -15
                && (z.getX() - x.getX()) < 10 && (z.getX() - x.getX()) > -5) {
            if (z.getCol().equals(x.getCol())) {
                d.add(x);
            } else if (x.getCol().equals(BLUE)) {
                if (meta.equals(NO)) {
                    meta = WINNERS;
                } else {
                    d.add(x);
                }
            } else {
                z.setY(z.getY() + ((x.getY() + 15) - z.getY()));
            }

        }
    }

    private void tickDown(Player z, Element x, ArrayList<Element> d) {
        if ((z.getY() - (x.getY() - 15)) > 0 && (z.getY() - (x.getY() - 15)) < 15
                && (z.getX() - x.getX()) < 10 && (z.getX() - x.getX()) > -5) {
            if (z.getCol().equals(x.getCol())) {
                d.add(x);
            } else if (x.getCol().equals(BLUE)) {
                if (meta.equals(NO)) {
                    meta = WINNERS;
                } else {
                    d.add(x);
                }
            } else {
                z.setY(z.getY() - (z.getY() - (x.getY() - 15)));
            }

        }
    }

    @Override
    public synchronized String getIP() {
        try {
            return NetUtils.getIPAddress();
        } catch (ConnectException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
