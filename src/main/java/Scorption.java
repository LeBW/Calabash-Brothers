import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Scorption extends VillainRole implements Runnable{
    public Scorption(int x, int y, Field field) {
        super(x, y, field);

        URL loc = getClass().getClassLoader().getResource("scorption-left.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
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
