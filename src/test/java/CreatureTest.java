import org.junit.Test;

import static org.junit.Assert.*;

public class CreatureTest {

    @Test
    public void readFromLine() {
        Huluwa huluwa = new Huluwa(60, 90, new Field(), 0, "red-right.png");
        huluwa.readFromLine("120 150 red-attack.png");
        assertEquals(huluwa.x(), 120);
        assertEquals(huluwa.y(), 150);
        assertEquals(huluwa.imageName, "red-attack.png");
    }
}