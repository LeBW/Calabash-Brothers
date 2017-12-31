import java.awt.*;

public class Thing2D {

    protected final int SPACE = 60;
    protected final int w = SPACE*15;
    protected final int h = SPACE*8 + SPACE/2;

    private int x;
    private int y;
    private Image image;

    public Thing2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return this.image;
    }
    public void setImage(Image img) {
        image = img;
    }
    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
