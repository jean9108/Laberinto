package arsw.laberinto.gui;

import java.awt.event.*;

public class InputHandler extends KeyAdapter {

    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }
}
