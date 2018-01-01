import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.security.Key;
import java.security.spec.ECField;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Field extends JPanel{
    //每一个单位所占的像素数
    private final int SPACE = 60;
    private final int OFFSET = 10;
    //界面的总宽度和长度
    private final int w = SPACE*15;
    private final int h = SPACE*8 + SPACE/2;
    //线程池
    private ExecutorService executorService;
    //输出流声明
    private BufferedWriter bufferedWriter;
    private Timer timer = new Timer();
    //thing2D的声明
    private ArrayList tiles = new ArrayList();
    private ArrayList<Huluwa> huluwas = new ArrayList<>();
    private GrandFather grandFather;
    private Snake snake;
    private Scorpion scorpion;
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
    //为方便葫芦娃初始化而设置的一个Map
    public final Map<Character, String> rankMap = new HashMap<>();

    public Field() {
        rankMap.put('0', "red");
        rankMap.put('1', "orange");
        rankMap.put('2', "yellow");
        rankMap.put('3', "green");
        rankMap.put('4', "cyan");
        rankMap.put('5', "blue");
        rankMap.put('6', "purple");
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }


    private final void initWorld() {

        executorService  = Executors.newCachedThreadPool();
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
               huluwas.add(new Huluwa(x, y, this, temp, rankMap.get(item)+"-right.png"));
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
               scorpion = new Scorpion(x, y, this);
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
        g.drawImage(new ImageIcon(getClass().getClassLoader().getResource("background.png")).getImage(), 0, 0, this);
        ArrayList world = new ArrayList();
        world.addAll(tiles);
        world.addAll(huluwas);
        world.add(grandFather);
        world.add(snake);
        world.add(scorpion);
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
                g.drawImage(item.getImage(), item.x(), item.y() - 10, this);
            }
            else if(item instanceof Snake) {
                g.drawImage(item.getImage(), item.x() + 10, item.y(), this);
            }
            else if(item instanceof Scorpion) {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }
            else if(item instanceof Minion) {
                g.drawImage(item.getImage(), item.x() + 5, item.y() + 10, this);
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

    public void checkForEnd() {
        if (allVillainRoleIsDead() || allDecentRoleIsDead()) {
            completed = true;
            executorService.shutdownNow();
            timer.cancel();
            try {
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Close objectOutputStream: " + e);
            }
        }
    }
    public boolean isCompleted() {
        return completed;
    }
    private Field getOuter() {
        return this;
    }
    private class TAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_SPACE) {

                String fileName = System.getProperty("user.dir") + "/record.txt";
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                } catch (Exception exception) {
                    System.out.println("Initialize objectOutputStream: " + exception);
                }
                timer = new Timer();
                timer.scheduleAtFixedRate(new RecordTask(getOuter(),  bufferedWriter), 0, 500);
                for(Huluwa huluwa: huluwas)
                    executorService.execute(huluwa);
                executorService.execute(grandFather);
                executorService.execute(snake);
                executorService.execute(scorpion);
                for(Minion minion: minions)
                    executorService.execute(minion);
            }
            else if (key == KeyEvent.VK_R) {
                restartLevel();
            }
            else if (key == KeyEvent.VK_L) {
                JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));
                int returnValue = jFileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jFileChooser.getSelectedFile();
                    System.out.println(selectedFile.getName());
                    if (!selectedFile.getName().equals("record.txt"))
                        return;
                    new ReplayTask(selectedFile).start();
                }
            }
        }

    }

    private void restartLevel() {
        tiles.clear();
        huluwas.clear();
        minions.clear();
        executorService.shutdownNow();
        initWorld();

        if(completed) {
            completed = false;
        }
        repaint();
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }
    public Snake getSnake() {
        return snake;
    }
    public Scorpion getScorpion() {
        return scorpion;
    }

    public GrandFather getGrandFather() {
        return grandFather;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public ArrayList<Huluwa> getHuluwas() {
        return huluwas;
    }

    ///判断坐标x，y是否能够进入
    public boolean isAvailable(int x,int y) {
        if (x < 0 || x >= w || y < SPACE/2 || y >= h)
            return false;
        for(Huluwa huluwa: huluwas) {
            if (huluwa.isAlive && huluwa.x() == x && huluwa.y() == y)
                return false;
        }
        if (snake.isAlive && snake.x() == x && snake.y() == y)
            return false;
        if (grandFather.isAlive && grandFather.x() == x && grandFather.y() == y)
            return false;
        if (scorpion.isAlive && scorpion.x() == x && scorpion.y() == y)
            return false;
        for (Minion minion: minions) {
            if(minion.isAlive && minion.x() == x && minion.y() == y)
                return false;
        }
        return true;
    }
    //返回坐标x，y的生物
    public Creature creatureAt(int x, int y) {
        if(x < 0 || x >= w || y < SPACE/2 || y >= h)
            return null;
        for(Huluwa huluwa: huluwas) {
            if(huluwa.isAlive && huluwa.x() == x && huluwa.y() == y)
                return huluwa;
        }
        if (snake.isAlive && snake.x() == x && snake.y() == y)
            return snake;
        if (grandFather.isAlive && grandFather.x() == x && grandFather.y() == y)
            return grandFather;
        if (scorpion.isAlive && scorpion.x() == x && scorpion.y() == y)
            return scorpion;
        for (Minion minion: minions) {
            if(minion.isAlive && minion.x() == x && minion.y() == y)
                return minion;
        }
        return null;
    }
    @Deprecated
    public void startWarBetween(DecentRole a, VillainRole b) {
        a.isInBattle = true;
        b.isInBattle = true;

        Random random = new Random();

        //正派60%胜利，反派40%胜利
        int flag = random.nextInt(10);

        /*
        try {
            wait(1000);
            repaint();
        } catch (Exception e) {
            System.out.println(a.getClass().getSimpleName() + b.getClass().getSimpleName() + e);
        }*/
        a.isInBattle = false;
        b.isInBattle = false;
        if (allVillainRoleIsDead() || allDecentRoleIsDead()) {
            completed = true;
            executorService.shutdownNow();
        }
    }
    private boolean allVillainRoleIsDead() {
        if (snake.isAlive || scorpion.isAlive)
            return false;
        for(Minion minion: minions) {
            if (minion.isAlive)
                return false;
        }
        return true;
    }
    private boolean allDecentRoleIsDead() {
        if (grandFather.isAlive)
            return false;
        for(Huluwa huluwa: huluwas) {
            if (huluwa.isAlive)
                return false;
        }
        return true;
    }
    //inner class

    class ReplayTask extends Thread{

        private  BufferedReader bufferedReader;
        ReplayTask(File file) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (Exception e) {
                System.out.println("Initialize bufferedReader: " + e);
            }
        }
        @Override
        public void run() {
            try {
                while (true) {
                    for (Huluwa huluwa: huluwas) {
                        String string = bufferedReader.readLine();
                        huluwa.readFromLine(string);
                    }
                    grandFather.readFromLine(bufferedReader.readLine());
                    snake.readFromLine(bufferedReader.readLine());
                    scorpion.readFromLine(bufferedReader.readLine());
                    for (Minion minion: minions)
                        minion.readFromLine(bufferedReader.readLine());
                    repaint();
                    sleep(500);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    bufferedReader.close();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                restartLevel();
                Thread.currentThread().interrupt();
            }
        }
    }
}
