
import java.io.BufferedWriter;
import java.util.TimerTask;

public class RecordTask extends TimerTask{
    private Field field;
    BufferedWriter bufferedWriter;
    RecordTask(Field field, BufferedWriter bufferedWriter) {
        //this.field = field;
        this.field = field;
        this.bufferedWriter = bufferedWriter;
    }



    @Override
    public void run() {
        String string = "";
        for (Huluwa huluwa: field.getHuluwas()) {
            string += huluwa;
        }
        string += field.getGrandFather();
        string += field.getSnake();
        string += field.getScorpion();
        for (Minion minion: field.getMinions())
            string += minion;
        try {
            bufferedWriter.write(string);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
