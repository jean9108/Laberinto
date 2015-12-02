package arsw.laberinto.gui;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class SpritesImageLoader {

    private String path;
    private BufferedImage image;

    public SpritesImageLoader(String path) {
        this.path = path;
    }

    public BufferedImage loadImage() throws IOException {
        image = ImageIO.read(getClass().getResource(path));
        return image;
    }

    public BufferedImage getImage(int top, int left, int width, int height) {
        BufferedImage img = image.getSubimage(top, left, width, height);
        return img;
    }
}
