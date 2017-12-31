import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class GrandFather extends DecentRole implements Runnable{
    public GrandFather(int x, int y, Field field) {
        super(x, y, field);

        URL loc = this.getClass().getClassLoader().getResource("grandfather-right.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }



    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (isAlive && !isInBattle) {
                //老爷爷以50%的几率上下移动
                Random random = new Random();
                int flag = random.nextInt(10);
                if (flag < 5)
                    moveDown();
                else
                    moveUp();
                try {
                    Thread.sleep(random.nextInt(1000) + 1000);
                    field.repaint();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                //老爷爷不会主动攻击敌人
            }
        }
    }
}
