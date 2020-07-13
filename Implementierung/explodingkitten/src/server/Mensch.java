package server;

import exceptions.NotYourRundeException;
import server.karten.Karte;
import java.util.ArrayList;

public class Mensch implements Spieler {

    String name;
    SpielRaum spielraum;
    int punkt;
    ArrayList<Karte> handkarte;

    /**
     * Konstruktor von Objekten der Klasse
     * @param nickname der Benutzername
     */
    public Mensch(String nickname) {
        this.name = nickname;
        this.handkarte = new ArrayList<>();
    }

    @Override
    public void karteLegen(Karte k) throws NotYourRundeException {
        spielraum.karteLegen(name,k);
    }

    @Override
    public void zugBeenden() throws NotYourRundeException {
        spielraum.zugBeenden(name);
    }

    @Override
    public void addKarte(Karte karte) {
        handkarte.add(karte);
    }

    @Override
    public void removeKarte(Karte karte) {
        handkarte.remove(karte);
    }

    @Override
    public String getname() {
        return name;
    }
}
