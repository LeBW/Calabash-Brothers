import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

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

    public int getRank() {
        return rank;
    }

    @Override
    public boolean moveLeft() {
        if (orientation == Orientation.Right) {
            char temp = (char)('0'+rank);
   //         System.out.println(temp + "-left.png");
            String imageName = field.rankMap.get(temp) + "-left.png";
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
        return super.moveLeft();
    }

    @Override
    public boolean moveRight() {
        if (orientation == Orientation.Left) {
            char temp = (char)('0'+rank);
            String imageName = field.rankMap.get(temp) + "-right.png";
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
        return super.moveRight();
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            if (isAlive && !isInBattle) {
                Random random = new Random();
                moveToVillainRole();
                try {
                    Thread.sleep(random.nextInt(1000) + 500);

                    field.repaint();

                } catch (Exception e) {

                }
                Creature creature = field.creatureAt(x() + SPACE, y());
                if (creature instanceof VillainRole && creature.isAlive)
                    field.startWarBetween(this, (VillainRole) creature);
                creature = field.creatureAt(x() - SPACE, y());
                if (creature instanceof VillainRole && creature.isAlive)
                    field.startWarBetween(this, (VillainRole)creature);
            }
        }
    }
    private void moveToVillainRole() {
        int minDistance = Integer.MAX_VALUE;
        VillainRole nearestEnemy = null;
        if (field.getSnake().isAlive && distanceTo(field.getSnake()) < minDistance) {
            minDistance = distanceTo(field.getSnake());
            nearestEnemy = field.getSnake();
        }
        if (field.getScorpion().isAlive && distanceTo(field.getScorpion()) < minDistance) {
            minDistance = distanceTo(field.getScorpion());
            nearestEnemy = field.getScorpion();
        }
        for(Minion minion: field.getMinions()) {
            if (minion.isAlive && distanceTo(minion) < minDistance) {
                minDistance = distanceTo(minion);
                nearestEnemy = minion;
            }
        }
        if (nearestEnemy != null) {

            if (nearestEnemy.y() > y()) {
                moveDown();
                return;
            }
            else if (nearestEnemy.y() < y()) {
                moveUp();
                return;
            }
            else if (nearestEnemy.x() > x())
            {
                moveRight();
                return;
            }
            else if (nearestEnemy.x() < x()) {
                moveLeft();
                return;
            }
        }
    }
}
