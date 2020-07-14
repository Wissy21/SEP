package test;

import main.exceptions.NichtGenugSpielerException;
import main.exceptions.NoExplodingKittenException;
import main.exceptions.NotYourRundeException;
import main.exceptions.SpielraumVollException;
import main.server.Nachricht;
import main.server.SpielRaum;
import main.server.karten.Karte;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class SpielRaumTest {
    SpielRaum raum = new SpielRaum();

    public SpielRaumTest() throws RemoteException {
    }

    @Test
    public void chatTest() {
        ArrayList<Nachricht> chat = new ArrayList<>();
        ArrayList<Nachricht> raumchat = raum.getMessage();
        assertEquals(chat,raumchat);
        Nachricht n = new Nachricht("Hallo","Ich","Heute");
        chat.add(n);
        raum.sendMessage("Hallo","Heute","Ich");
        raumchat = raum.getMessage();
        for(int i= 0;i<chat.size();i++) {
            for(int j = i;j<raumchat.size();j++) {
                assertEquals(chat.get(i).sender,raumchat.get(j).sender);
                assertEquals(chat.get(i).message,raumchat.get(j).message);
                assertEquals(chat.get(i).time,raumchat.get(j).time);
            }
        }
    }

    @Test
    public void botTest() {
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertThrows(SpielraumVollException.class, ()->raum.botHinzufuegen());
        assertTrue(raum.spielraumVerlassen("BO1"));
        assertTrue(raum.spielraumVerlassen("BO2"));
        assertTrue(raum.spielraumVerlassen("BO3"));
        assertTrue(raum.spielraumVerlassen("BO4"));
        assertTrue(raum.spielraumVerlassen("BO5"));
        assertEquals(0, raum.anzahlSpieler);
    }
    @Test
    public void betretenTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertDoesNotThrow(()->raum.botHinzufuegen());
        assertThrows(SpielraumVollException.class, ()->raum.betreten("Tester3"));
        assertFalse(raum.spielraumVerlassen("Tester2"));
        assertTrue(raum.spielraumVerlassen("Tester"));
        assertTrue(raum.spielraumVerlassen("BO1"));
        assertTrue(raum.spielraumVerlassen("BO2"));
        assertTrue(raum.spielraumVerlassen("BO3"));
        assertEquals(0, raum.anzahlSpieler);
    }

    @Test
    public void spielStartenTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertTrue(raum.isRunning());
        assertEquals(raum.getStapelSize(),35);
        assertNotNull(raum.getCurrent());
    }

    @Test
    public void spielStartenTest2() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.betreten("Tester3"));
        assertDoesNotThrow(()->raum.betreten("Tester4"));
        assertDoesNotThrow(()->raum.betreten("Tester5"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertEquals(raum.getStapelSize(),16);
        assertNotNull(raum.getCurrent());
        assertTrue(raum.isRunning());
    }

    @Test
    public void karteLegenTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertThrows(NotYourRundeException.class,()->raum.karteLegen(raum.getReihenfolge()[1].getNickname(),new Karte("0","Katze1")));
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[1].getNickname(),new Karte("0","Noe")));
        assertTrue(raum.isNoe());
        assertDoesNotThrow(()->raum.karteLegen(raum.amZug(),new Karte("0","Katze1")));
        assertDoesNotThrow(()->Thread.sleep(1000));
        assertFalse(raum.isNoe());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[1].getNickname(),new Karte("0","Noe")));
        assertTrue(raum.isNoe());
        Stack<Karte> ablage = new Stack<>();
        ablage.push(new Karte("0","Noe"));
        ablage.push(new Karte("0","Katze1"));
        ablage.push(new Karte("0","Noe"));
        assertEquals(ablage,raum.ablagestapel);

    }

    @Test
    public void entschaerfungTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertThrows(NoExplodingKittenException.class,()->raum.karteLegen(raum.amZug(),new Karte("0","Entschaerfung")));
        assertDoesNotThrow(()->raum.setExpolding(true));
        assertTrue(raum.isExpolding());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[0].getNickname(),new Karte("0","Entschaerfung")));
        assertFalse(raum.isExpolding());
    }

    @Test
    public void angriffTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertFalse(raum.isAngriff());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[0].getNickname(),new Karte("0","Angriff")));
        assertDoesNotThrow(()->Thread.sleep(5500));
        assertTrue(raum.isAngriff());
        assertEquals(raum.amZug(),raum.getReihenfolge()[1].getNickname());
    }

    @Test
    public void hopsTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[0].getNickname(),new Karte("0","Hops")));
        assertDoesNotThrow(()->Thread.sleep(5500));
        assertEquals(raum.amZug(),raum.getReihenfolge()[1].getNickname());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[1].getNickname(),new Karte("0","Angriff")));
        assertDoesNotThrow(()->Thread.sleep(5500));
        assertEquals(raum.amZug(),raum.getReihenfolge()[0].getNickname());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[0].getNickname(),new Karte("0","Hops")));
        assertDoesNotThrow(()->Thread.sleep(5500));
        assertEquals(raum.amZug(),raum.getReihenfolge()[0].getNickname());
    }

    @Test
    public void noeTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertFalse(raum.isNoe());
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[0].getNickname(),new Karte("0","Hops")));
        assertDoesNotThrow(()->Thread.sleep(2500));
        assertDoesNotThrow(()->raum.karteLegen(raum.getReihenfolge()[1].getNickname(),new Karte("0","Noe")));
        assertTrue(raum.isNoe());
        assertDoesNotThrow(()->Thread.sleep(3000));
        assertFalse(raum.isNoe());
        assertEquals(raum.amZug(),raum.getReihenfolge()[0].toString());
    }

    @Test
    public void explodiertTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertThrows(NichtGenugSpielerException.class,()->raum.spielStarten());
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        ArrayList<Karte> karten = raum.getCurrent().getHandkarte();
        assertDoesNotThrow(()->raum.explodiert("Tester",new Karte("0","ExplodingKitten")));
        karten.add(0,new Karte("0","ExplodingKitten"));
        Stack<Karte> ablage =raum.ablagestapel;
        for(int i = karten.size()-1;i>=0;i--) {
            assertEquals(karten.get(i), ablage.pop());
        }
    }

    @Test
    public void wunschTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertDoesNotThrow(()->raum.setAusgewaehler(raum.reihenfolge[1]));
        assertEquals(raum.ausgewahlter,raum.reihenfolge[1]);
        Karte tauschkarte = raum.ausgewahlter.handkarte.get(1);
        assertDoesNotThrow(()->raum.abgeben(raum.ausgewahlter.toString(),tauschkarte));
        assertTrue(raum.getCurrent().handkarte.contains(tauschkarte));
        assertFalse(raum.ausgewahlter.handkarte.contains(tauschkarte));
    }

    @Test
    public void zugBeendenTest() {
        assertDoesNotThrow(()->raum.betreten("Tester"));
        assertDoesNotThrow(()->raum.betreten("Tester2"));
        assertDoesNotThrow(()->raum.spielStarten());
        assertThrows(NotYourRundeException.class,()->raum.zugBeenden(raum.getReihenfolge()[1].getNickname()));
        assertDoesNotThrow(()->raum.zugBeenden(raum.current.getNickname()));
    }
}