package server;

import server.karten.Karte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Spieler implements Serializable {

    String nickname;
    int punkt;
    public ArrayList<Karte> handkarte = new ArrayList<>();
    boolean isBot;

    public Spieler(boolean isBot) {
        this.isBot = isBot;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<Karte> getHandkarte() {
        return handkarte;
    }

    public void karteLegen(Karte k) {
    }

    public Karte zugBeenden() {

        return null;
    }
}
