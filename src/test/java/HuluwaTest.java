import org.junit.Test;

import static org.junit.Assert.*;

public class HuluwaTest {

    @Test
    public void setInBattle() {
        Huluwa huluwa = new Huluwa(60, 90, new Field(), 0, "red-right.png");
        huluwa.setInBattle(true);
        assertEquals(huluwa.imageName, "red-attack.png");
    }
}