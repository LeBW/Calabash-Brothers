import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Tile extends Thing2D {
    private boolean isAvailable = true;   //指示该tile是否为空
    private Creature holder = null;   //存放该tile上的生物体
    public Tile(int x, int y) {
        super(x, y);

        URL loc = this.getClass().getClassLoader().getResource("tile.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setHolder(Creature holder) {
        this.holder = holder;
    }
}
