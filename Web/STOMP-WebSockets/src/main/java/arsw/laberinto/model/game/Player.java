package arsw.laberinto.model.game;

public class Player extends Element {

    private double velX;
    private double velY;
    private String id;
    public static int GAMEWIDTH = 0;
    public static int GAMEHEIGHT = 0;
    public static int GAMESCALE = 0;

    public Player(int x, int y, int gw, int gh, int gs, int wd, int hg, String cr) {
        super(x, y, wd, hg, cr);
        GAMEHEIGHT = gh;
        GAMEWIDTH = gw;
        GAMESCALE = gs;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void tick() {
        x += velX;
        y += velY;

        // To avoid player go outside the arena.		
        if (x <= width) {
            x = width;
        }
        if (x >= (GAMEWIDTH * GAMESCALE) - (width / 4)) {
            x = (GAMEWIDTH * GAMESCALE) - (width / 4);
        }
        if (y <= height) {
            y = height;
        }
        if (y >= (GAMEHEIGHT * GAMESCALE) - (height / 4)) {
            y = (GAMEHEIGHT * GAMESCALE) - (height / 4);
        }
    }
}
