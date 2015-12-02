package arsw.laberinto.gui;

import java.awt.*;

public class BackgroundRenderer {

    public void render(Graphics g, Canvas c) {
        g.setColor(Color.BLACK);
        g.fillOval(-c.getWidth(), -c.getHeight(), c.getWidth() * 2, c.getHeight() * 2);
        g.fillOval(c.getWidth() / 100, c.getHeight() / 100, c.getWidth() * 2, c.getHeight() * 2);
    }
}
