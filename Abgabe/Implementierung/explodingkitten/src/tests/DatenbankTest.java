package tests;

import exceptions.*;
import org.junit.jupiter.api.Test;
import server.datenbankmanager.DBmanager;
import server.datenbankmanager.Row;

import static org.junit.jupiter.api.Assertions.*;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class DatenbankTest {
    DBmanager db = new DBmanager();

    public DatenbankTest() throws RemoteException {
    }


    @Test
    public void loeschenTest() {
        assertDoesNotThrow(()-> db.spielerRegistrieren("Tester","pass","pass"));
        assertDoesNotThrow(() -> db.kontoLoeschen("Tester"));
    }

    @Test
    public void registrierenTest() {
        assertDoesNotThrow(()-> db.spielerRegistrieren("Tester","pass","pass"));
        assertThrows(UserNameAlreadyExistsException.class,()-> db.spielerRegistrieren("Tester","pass","pass"));
        assertThrows(NotEqualPassWordException.class,()-> db.spielerRegistrieren("Tester2","pass","notpass"));
        assertDoesNotThrow(()-> db.kontoLoeschen("Tester"));

    }
    @Test
    public void anmeldenAbmeldenTest() {
        assertDoesNotThrow(() -> db.spielerRegistrieren("Tester", "pass", "pass"));
        assertDoesNotThrow(() -> db.spielerAnmelden("Tester", "pass"));
        assertThrows(AccountOnlineException.class, () -> db.spielerAnmelden("Tester", "pass"));
        assertDoesNotThrow(() -> db.spielerAbmelden("Tester"));
        assertThrows(WrongPasswordException.class, () -> db.spielerAnmelden("Tester", "notpass"));
        assertDoesNotThrow(() -> db.spielerAnmelden("Tester", "pass"));
        assertDoesNotThrow(() -> db.kontoLoeschen("Tester"));
    }

    @Test
    public void datenAendernTest() {
        assertDoesNotThrow(() -> db.spielerRegistrieren("Tester", "pass", "pass"));
        assertDoesNotThrow(() -> db.spielerRegistrieren("Tester2", "pass", "pass"));
        assertDoesNotThrow(() -> db.datenAendern("Tester","TesterNeu", "pass", "pass"));
        assertThrows(UserNameAlreadyExistsException.class,() -> db.datenAendern("Tester2","TesterNeu", "pass", "pass"));
        assertThrows(NotEqualPassWordException.class,() -> db.datenAendern("Tester2","TesterNeu2", "pass", "notpass"));
        assertDoesNotThrow(() -> db.datenAendern("Tester2","TesterNeu2", "pass", "pass"));
        assertDoesNotThrow(() -> db.kontoLoeschen("TesterNeu"));
        assertDoesNotThrow(() -> db.kontoLoeschen("TesterNeu2"));

    }

    @Test
    public void raumErstellenTest() {
        assertDoesNotThrow(()->db.raumErstellen("Tester","Testraum"));
        assertThrows(RaumnameVergebenException.class,()->db.raumErstellen("Tester","Testraum"));
        assertDoesNotThrow(()->db.raumVerlassen("Tester","Testraum"));
    }

    @Test
    public void raumBeitretenVerlassenTest() {
        assertDoesNotThrow(()->db.raumErstellen("Tester","Testraum"));
        assertDoesNotThrow(()->db.raumBeitreten("Tester2","Testraum"));
        assertDoesNotThrow(()->db.raumBeitreten("Tester3","Testraum"));
        assertDoesNotThrow(()->db.raumBeitreten("Tester4","Testraum"));
        assertDoesNotThrow(()->db.raumBeitreten("Tester5","Testraum"));
        assertThrows(SpielraumVollException.class,()->db.raumBeitreten("Tester6","Testraum"));
        assertThrows(RaumNotExistException.class,()->db.raumBeitreten("Tester6","Testraum2"));
        assertDoesNotThrow(()->db.raumVerlassen("Tester","Testraum"));
        assertDoesNotThrow(()->db.raumVerlassen("Tester2","Testraum"));
        assertDoesNotThrow(()->db.raumVerlassen("Tester3","Testraum"));
        assertDoesNotThrow(()->db.raumVerlassen("Tester4","Testraum"));
        assertDoesNotThrow(()->db.raumVerlassen("Tester5","Testraum"));
        assertThrows(RaumNotExistException.class,()->db.raumBeitreten("Tester6","Testraum"));
    }

    @Test
    public void bestenlisteTest() {
        assertDoesNotThrow(() -> db.spielerRegistrieren("Tester", "pass", "pass"));
        assertDoesNotThrow(() -> db.spielerRegistrieren("Tester2", "pass", "pass"));

        AtomicReference<ArrayList<Row>> ref = new AtomicReference<>();
        ArrayList<Row> rows;
        assertDoesNotThrow(()->ref.set(db.getBestenliste()));
        rows = ref.get();
        for(Row r : rows) {
            assertEquals(r.punkteProperty().get(),0);
            assertEquals(r.platzProperty().get(),1);
        }

        assertDoesNotThrow(()-> db.siegEintragen("Tester"));
        assertDoesNotThrow(()->ref.set(db.getBestenliste()));
        rows = ref.get();
        for(Row r : rows) {
            if(r.nameProperty().get().equals("Tester")) {
                assertEquals(r.punkteProperty().get(), 1);
                assertEquals(r.platzProperty().get(), 1);
            } else {
                assertEquals(r.punkteProperty().get(), 0);
                assertEquals(r.platzProperty().get(), 2);
            }
        }
        assertDoesNotThrow(() -> db.kontoLoeschen("Tester"));
        assertDoesNotThrow(() -> db.kontoLoeschen("Tester2"));
    }
}

