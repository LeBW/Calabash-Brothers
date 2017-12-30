import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GrandFather extends DecentRole implements Runnable{
    public GrandFather(int x, int y, Field field) {
        super(x, y, field);

        URL loc = this.getClass().getClassLoader().getResource("grandfather.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public boolean moveUp() {
        return false;
    }

    @Override
    public boolean moveDown() {
        return false;
    }

    @Override
    public boolean moveLeft() {
        return false;
    }

    @Override
    public boolean moveRight() {
        return false;
    }

    @Override
    public void run() {

    }
}
