import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Scorpion extends VillainRole implements Runnable{
    public Scorpion(int x, int y, Field field) {
        super(x, y, field);

        URL loc = getClass().getClassLoader().getResource("scorpion-left.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
        if (!isAlive)
            setImage(new ImageIcon(getClass().getClassLoader().getResource("scorpion-dead.png")).getImage());
    }
    @Override
    public void run() {
        while(!Thread.interrupted()) {
            if (isAlive && !isInBattle) {
                Random random = new Random();
                moveLeft();
                try {
                    Thread.sleep(random.nextInt(1000) + 1000);
                    field.repaint();
                } catch (Exception e) {
                    System.out.println("Scorpion: " + e);
                }

            }
        }
    }
}
