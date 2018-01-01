import javax.swing.*;
import java.io.Serializable;
import java.util.Random;

public abstract class DecentRole extends Creature implements Serializable{
    public DecentRole(int x, int y, Field field, String imageName) {
        super(x, y, field, imageName);
        orientation = Orientation.Right;
    }

    @Override
    public void startWarWith(Creature creature) {
        if (creature instanceof VillainRole) {
            setInBattle(true);
            creature.setInBattle(true);
            field.repaint();

            try {
                Thread.sleep(1000);
                Random random = new Random();
                //正派60%胜利，反派40%
                int flag = random.nextInt(10);
                if (flag <= 5) {
                    creature.setAlive(false);
                } else {
                    setAlive(false);
                }
                field.repaint();
            } catch (Exception e) {
                System.out.println(e);
                Thread.currentThread().interrupt();
            } finally {
                setInBattle(false);
                creature.setInBattle(false);
            }
            field.checkForEnd();
        }
    }
    protected void moveToVillainRole() {
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
