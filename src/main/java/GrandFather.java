import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class GrandFather extends DecentRole implements Runnable {
    public GrandFather(int x, int y, Field field) {
        super(x, y, field, "grandfather-right.png");

        URL loc = this.getClass().getClassLoader().getResource("grandfather-right.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
        if (!isAlive) {
            imageName = "grandfather-dead.png";
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
    }

    @Override
    public boolean moveLeft() {
        if (orientation == Orientation.Right) {
            imageName = "grandfather-left.png";
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
        return super.moveLeft();
    }

    @Override
    public boolean moveRight() {
        if (orientation == Orientation.Left) {
            imageName = "grandfather-right.png";
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
        return super.moveRight();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (isAlive && !isInBattle) {
                //当葫芦娃没有全部死亡时，老爷爷在左边指挥战争。若葫芦娃全部死亡，老爷爷孤军奋战，奋勇杀敌。
                ArrayList<Huluwa> huluwaArrayList = field.getHuluwas();
                boolean allDead = true;
                Random random = new Random();
                for(Huluwa huluwa: huluwaArrayList) {
                    if (huluwa.isAlive) {
                        allDead = false;
                        break;
                    }
                }
                if (!allDead) {
                    int flag = random.nextInt(10);
                    if (flag < 5)
                        moveDown();
                    else
                        moveUp();
                }
                else {
                    moveToVillainRole();
                }
                try {
                    Thread.sleep(random.nextInt(1000) + 1000);
                    field.repaint();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
