import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Huluwa extends DecentRole implements Runnable{
    private int rank;  //七个葫芦娃的排行

    public Huluwa(int x, int y, Field field, int rank, String imageName) {
        super(x, y, field);

        this.rank = rank;
        URL loc = this.getClass().getClassLoader().getResource(imageName);
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
