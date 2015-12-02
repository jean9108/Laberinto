package arsw.laberinto.model.game;

public class Element {

    protected int x;
    protected int y;
    protected int width = 0;
    protected int height = 0;
    protected String col;

    public Element(int x, int y, int width, int height, String col) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.col = col;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }
}
