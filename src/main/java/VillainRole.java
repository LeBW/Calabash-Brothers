import java.util.Random;

public abstract class VillainRole extends Creature{
    public VillainRole(int x, int y, Field field, String imageName) {
        super(x, y, field, imageName);
        orientation = Orientation.Left;
    }
    @Override
    public void startWarWith(Creature creature) {
        if (creature instanceof DecentRole) {
            setInBattle(true);
            creature.setInBattle(true);
            field.repaint();
            try {
                Thread.sleep(1000);
                Random random = new Random();
                //正派60%胜利，反派40%
                int flag = random.nextInt(10);
                if (flag > 6) {
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
}
