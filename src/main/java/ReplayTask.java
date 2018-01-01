import java.io.*;
import java.util.ArrayList;
import java.util.TimerTask;
/*
public class ReplayTask extends Thread{
    private File file;
    private Field field;
    private ObjectInputStream objectInputStream;
    ReplayTask(Field field, File file) {
        this.field = field;
        this.file = file;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println("Initialize objectInputStream: " + e);
        }
    }
    @Override
    public void run() {
        try {
            while (true) {
                field.huluwas = (ArrayList<Huluwa>)objectInputStream.readObject();
                GrandFather grandFather = (GrandFather)objectInputStream.readObject();

            }
        }
        catch (EOFException e) {
            Thread.currentThread().interrupt();
        }
        catch (Exception e) {
            System.out.println("read record: " + e);
        }
    }
}
*/