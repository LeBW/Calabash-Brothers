import javax.swing.*;
import java.io.Serializable;

public abstract class Creature extends Thing2D{
    protected transient Field field;
    public Creature(int x, int y, Field field, String imageName) {
        super(x, y);
        this.field = field;
        this.imageName = imageName;
    }
    //生物特有的属性
    enum Orientation { Left, Right}
    Orientation orientation;    //面朝的方向
    boolean isInBattle = false;   //是否在战斗中
    boolean isAlive = true;   //是否还活着
    String imageName;

    //生物移动函数
    public boolean moveUp() {
        if(field.isAvailable(x(), y()-SPACE)) {
            setY(y() - SPACE);
            return true;
        }
        else return false;
    }
    public boolean moveDown() {
        if(field.isAvailable(x(), y()+SPACE)) {
            setY(y() + SPACE);
            return true;
        }
        else return false;
    }
    public boolean moveLeft() {
        orientation = Orientation.Left;
        if(field.isAvailable(x()-SPACE, y())) {
            setX(x() - SPACE);
            return true;
        }
        else return false;
    }
    public boolean moveRight() {
        orientation = Orientation.Right;
        if(field.isAvailable(x()+SPACE, y())) {
            setX(x() + SPACE);
            return true;
        }
        else return false;
    }
    public void readFromLine(String string) {
        String[] arr = string.split(" ");
        setX(Integer.parseInt(arr[0]));
        setY(Integer.parseInt(arr[1]));
        imageName = arr[2];
        setImage(new ImageIcon(getClass().getClassLoader().getResource(imageName)).getImage());
    }
    //距另一个生物的距离
    public int distanceTo(Creature creature) {
        return Math.abs(x() - creature.x()) + Math.abs(y() - creature.y());
    }
    public void setInBattle(boolean isInBattle) {
        this.isInBattle = isInBattle;
    }
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public void startWarWith(Creature creature) {

    }

    @Override
    public String toString() {
        return x() + " " + y() + " " + imageName + "\n";
    }
}
