package server;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

public class SpielerTest {
    SpielRaum raum = new SpielRaum();

    public SpielerTest() throws RemoteException {
    }

    @Test
    public void equalsTest() {
        Spieler sp1 = new Spieler(true);
        sp1.nickname = "BOT1";
        Spieler sp2 = new Spieler(true);
        sp2.nickname = "BOT1";
        assertEquals(sp1, sp2);
        assertNotEquals(sp1,"Test");

    }
    @Test
    public void zugTest() {
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.spielStarten());
    }
}
