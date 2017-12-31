import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Minion extends VillainRole implements Runnable{
    public Minion(int x, int y, Field field) {
        super(x, y, field);

        URL loc = getClass().getClassLoader().getResource("minion-left.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }

    @Override
    public boolean moveLeft() {
        if (orientation == Orientation.Right)
            setImage(new ImageIcon(getClass().getClassLoader().getResource("minion-left.png")).getImage());
        return super.moveLeft();
    }

    @Override
    public boolean moveRight() {
        if (orientation == Orientation.Left)
            setImage(new ImageIcon(getClass().getClassLoader().getResource("minion-right.png")).getImage());
        return super.moveRight();
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            if (isAlive && !isInBattle) {
                Random random = new Random();
                if (orientation == orientation.Left) {
                    if (!moveLeft()) {
                        moveRight();
                    }
                }
                else {
                    if (!moveRight())
                        moveLeft();
                }
                try {
                    Thread.sleep(1500 + random.nextInt(1500));
                    field.repaint();
                } catch (Exception e) {

                }
                Creature creature = field.creatureAt(x() + SPACE, y());
                if (creature instanceof DecentRole && creature.isAlive && !creature.isInBattle)
                    field.startWarBetween((DecentRole)creature,this);
                creature = field.creatureAt(x() - SPACE, y());
                if (creature instanceof DecentRole && creature.isAlive && !creature.isInBattle)
                    field.startWarBetween((DecentRole)creature, this);
            }
        }
    }
}
