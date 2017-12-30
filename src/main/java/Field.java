import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Field extends JPanel{
    //每一个单位所占的像素数
    private final int SPACE = 60;
    private final int OFFSET = 10;
    //界面的总宽度和长度
    private final int w = SPACE*15;
    private final int h = SPACE*8 + SPACE/2;
    //thing2D的声明
    private ArrayList tiles = new ArrayList();
    private ArrayList<Huluwa> huluwas = new ArrayList<>();
    private GrandFather grandFather;
    private Snake snake;
    private Scorption scorption;
    private ArrayList<Minion> minions = new ArrayList<>();

    private boolean completed = false;

    private String level =
                    ".............m.\n" +
                    "............m..\n" +
                    "..2........m...\n" +
                    "...1.....smm...\n" +
                    ".g650....nmm...\n" +
                    "...3.......m...\n" +
                    "..4.........m..\n" +
                    ".............m."  ;
    public Field() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() { return this.w;}

    public int getBoardHeight() { return this.h;
    }
    private final void initWorld() {
        //为方便葫芦娃初始化而设置的一个Map
        final Map<Character, String> rankMap = new HashMap<>();
        rankMap.put('0', "red-right.png");
        rankMap.put('1', "orange-right.png");
        rankMap.put('2', "yellow-right.png");
        rankMap.put('3', "green-right.png");
        rankMap.put('4', "cyan-right.png");
        rankMap.put('5', "blue-right.png");
        rankMap.put('6', "purple-right.png");
       //初始化草地
        for(int x = 0; x < w; x += SPACE) {
           for(int y = SPACE/2; y < h; y += SPACE) {
               tiles.add(new Tile(x, y));
           }
        }
        //初始化Creature
        int x = 0, y = SPACE/2;
        for(int i = 0; i < level.length(); i++) {
           char item = level.charAt(i);

           if(item == '\n') {
               y += SPACE;
               x = 0;
           }
           else if (item == '.') {
               x += SPACE;
           }
           else if (Character.isDigit(item)) {
               int temp = item - '0';
               huluwas.add(new Huluwa(x, y, this, temp, rankMap.get(item)));
               x += SPACE;
           }
           else if (item == 'g') {
               grandFather = new GrandFather(x, y, this);
               x += SPACE;
           }
           else if (item == 's') {
               snake = new Snake(x, y, this);
               x += SPACE;
           }
           else if (item == 'n') {
               scorption = new Scorption(x, y, this);
               x += SPACE;
           }
           else if (item == 'm') {
               minions.add(new Minion(x, y, this));
               x += SPACE;
           }
           else {
                   //TODO
           }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    private void buildWorld(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());

        ArrayList world = new ArrayList();
        world.addAll(tiles);
        world.addAll(huluwas);
        world.add(grandFather);
        world.add(snake);
        world.add(scorption);
        world.addAll(minions);
        for(int i = 0; i < world.size(); i++) {
            Thing2D item = (Thing2D) world.get(i);
            if(item instanceof Tile) {
                g.drawImage(item.getImage(), item.x(), item.y() + 19 + OFFSET, this);
            }
            else if(item instanceof Huluwa) {
                g.drawImage(item.getImage(), item.x() + 10, item.y(), this);
            }
            else if(item instanceof GrandFather) {
                g.drawImage(item.getImage(), item.x() + 10, item.y(), this);
            }
            else if(item instanceof Snake) {
                g.drawImage(item.getImage(), item.x() + 10, item.y(), this);
            }
            else if(item instanceof Scorption) {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }
            else if(item instanceof Minion) {
                g.drawImage(item.getImage(), item.x() + 10, item.y(), this);
            }
            else {
                //TODO
            }
        }
        if (completed) {
            g.setColor(new Color(0, 0, 0));
            g.drawString("Completed", 25, 20);
        }
    }

    private class TAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent e) {
            if(completed) {
                return;
            }
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_SPACE) {
                //TODO Start
            }
            else if (key == KeyEvent.VK_R) {
                restartLevel();
            }
        }

    }

    private void restartLevel() {
        tiles.clear();
        initWorld();
        if(completed) {
            completed = false;
        }
    }
}
