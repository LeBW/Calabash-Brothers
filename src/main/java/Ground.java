import javax.swing.*;

public final class Ground extends JFrame{
    private final int OFFSET = 40;
    public Ground() {
        initUI();
    }

    public void initUI() {
        Field field = new Field();
        add(field);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth(), field.getBoardHeight() + OFFSET);
        setLocationRelativeTo(null);
        setTitle("Ground");
    }
}
