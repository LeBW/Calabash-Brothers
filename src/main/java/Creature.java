public abstract class Creature extends Thing2D{
    private Field field;
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
    abstract public boolean moveUp();
    abstract public boolean moveDown();
    abstract public boolean moveLeft();
    abstract public boolean moveRight();
}
