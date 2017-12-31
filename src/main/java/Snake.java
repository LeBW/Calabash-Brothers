import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Snake extends VillainRole implements Runnable{

    public Snake(int x, int y, Field field) {
        super(x, y, field);

        URL loc = this.getClass().getClassLoader().getResource("snake-left.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
        if (!isAlive)
            setImage(new ImageIcon(getClass().getClassLoader().getResource("snake-dead.png")).getImage());
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if(isAlive && !isInBattle) {
                Random random = new Random();
                moveLeft();
                try {
                    Thread.sleep(1000 + random.nextInt(1000));
                    field.repaint();
                } catch (Exception e) {
                    System.out.println("Snake: " + e);
                }
            }
        }
    }
}
