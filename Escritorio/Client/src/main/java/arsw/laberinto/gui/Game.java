package arsw.laberinto.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import arsw.laberinto.game.*;

public class Game extends Canvas {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 325;
    public static final int HEIGHT = 300;
    public static final int SCALE = 2;
    private static final String CYAN = "CYAN";
    private static final String ORANGE = "ORANGE";
    private static final String GREEN = "GREEN";
    private static final String RED = "RED";
    private static final String WHITE = "WHITE";
    private static final String BLUE = "BLUE";
    public final String TITLE = "Laberinto Multiplayer";
    private String player, lag;
    private boolean running = false;
    private BackgroundRenderer backgRenderer;
    private final IMapa mp;
    private static HashMap<String, Color> colores = new HashMap<>();

    public Game(IMapa mp) {
        this.mp = mp;
        lag = "";
    }

    public void assignName(String name) throws Exception {
        mp.asignarIdPlayer(name);
        setPlayer(name);
    }

    public void sacarName(String name) {
        mp.sacarIdPlayer(name);
        setPlayer(null);
    }

    public void init() {
        requestFocus();
        addKeyListener(new InputHandler(this));
        backgRenderer = new BackgroundRenderer();
    }

    public void keyPressed(KeyEvent e) {
        mp.movimientoJugadores(player, e.getKeyCode(), 2);
    }

    public void keyReleased(KeyEvent e) {
        mp.movimientoJugadores(player, e.getKeyCode(), 0);
    }

    public void start() {
        running = true;
        init();

        long lastTime = System.nanoTime();
        final double numOfTicks = 60.0;
        double ns = 1000000000 / numOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                lag = updates + "ticks, fps " + frames;
                updates = 0;
                frames = 0;
            }
        }
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    /*
     * Run the ticks of all game components.
     */
    public void tick() {
        mp.tick();
    }

    /*
     * Render overall game components.
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        backgRenderer.render(g, this);

        renderPlayer(g);
        renderWall(g);
        g.setColor(Color.BLUE);
        g.drawString(mp.getMeta(), 510, 80);
        g.drawString(lag, 510, 580);

        g.dispose();
        bs.show();
    }

    private void renderPlayer(Graphics g) {
        ArrayList<Integer> posX2 = mp.getJugadoresX();
        ArrayList<Integer> posY2 = mp.getJugadoresY();
        ArrayList<String> posC2 = mp.getJugadoresC();
        int k = mp.getJugadoresW(), l = mp.getJugadoresH();
        for (int q = 0; q < posX2.size(); q++) {

            int i = posX2.get(q), j = posY2.get(q);
            String c = posC2.get(q);
            g.setColor(cargarColores().get(c));
            Polygon sf = new Polygon();
            sf.addPoint(i, j - l);
            sf.addPoint(i - k, j);
            sf.addPoint(i + k, j);
            sf.addPoint(i, j + l);
            g.fillPolygon(sf);

            g.setColor(cargarColores().get(c));
            Polygon sf2 = new Polygon();
            sf2.addPoint(i, j + l);
            sf2.addPoint(i - k, j);
            sf2.addPoint(i + k, j);
            g.fillPolygon(sf2);
        }
    }

    private void renderWall(Graphics g) {

        ArrayList<Integer> posX = mp.getRestriccionX();
        ArrayList<Integer> posY = mp.getRestriccionY();
        ArrayList<String> posC = mp.getRestriccionC();
        int k = mp.getRestriccionW(), l = mp.getRestriccionH();
        for (int q = 0; q < posX.size() && q < posC.size(); q++) {

            int i = posX.get(q), j = posY.get(q);
            String c = posC.get(q);

            g.setColor(cargarColores().get(c));
            Polygon sf = new Polygon();
            sf.addPoint(i - k, j - l);
            sf.addPoint(i + k, j - l);
            sf.addPoint(i + k, j + l);
            g.fillPolygon(sf);
        }
    }

    private HashMap<String, Color> cargarColores() {
        if (colores.isEmpty()) {
            colores.put(BLUE, Color.BLUE);
            colores.put(CYAN, Color.CYAN);
            colores.put(GREEN, Color.GREEN);
            colores.put(ORANGE, Color.ORANGE);
            colores.put(RED, Color.RED);
            colores.put(WHITE, Color.WHITE);
        }
        return colores;
    }
}
