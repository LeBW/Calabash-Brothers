public abstract class Creature extends Thing2D{
    protected Field field;
    public Creature(int x, int y, Field field) {
        super(x, y);
        this.field = field;
    }
    //生物特有的属性
    enum Orientation { Left, Right}
    Orientation orientation;    //面朝的方向
    boolean isInBattle = false;   //是否在战斗中
    boolean isAlive = true;   //是否还活着

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
    public int distanceTo(Creature creature) {
        return Math.abs(x() - creature.x()) + Math.abs(y() - creature.y());
    }
}
