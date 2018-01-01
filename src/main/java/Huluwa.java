import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.net.URL;
import java.util.Random;

public class Huluwa extends DecentRole implements Runnable {
    private int rank;  //七个葫芦娃的排行

    public Huluwa(int x, int y, Field field, int rank, String imageName) {
        super(x, y, field, imageName);

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
            this.imageName = imageName;
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
        return super.moveLeft();
    }

    @Override
    public boolean moveRight() {
        if (orientation == Orientation.Left) {
            char temp = (char)('0'+rank);
            String imageName = field.rankMap.get(temp) + "-right.png";
            this.imageName = imageName;
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
                    System.out.println("hulu"+ rank + e);
                    Thread.currentThread().interrupt();
                }
                Creature creature = field.creatureAt(x() + SPACE, y());
                if (creature instanceof VillainRole && creature.isAlive)
                    startWarWith(creature);
                creature = field.creatureAt(x() - SPACE, y());
                if (creature instanceof VillainRole && creature.isAlive)
                    startWarWith(creature);
            }
        }
    }

    @Override
    public void setInBattle(boolean isInBattle) {
        super.setInBattle(isInBattle);
        if (isInBattle) {
            String imageName = field.rankMap.get((char) ('0' + rank)) + "-attack.png";
            this.imageName = imageName;
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
        else if (isAlive){
            String imageName = field.rankMap.get((char) ('0' + rank)) + "-right.png";
            this.imageName = imageName;
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
        if (!isAlive) {
            String imageName = field.rankMap.get((char) ('0' + rank)) + "-dead.png";
            this.imageName = imageName;
            System.out.println(imageName);
            setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
        }
    }

}
