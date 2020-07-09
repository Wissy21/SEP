package server;

import server.karten.Karte;

import java.util.List;

public class Spieler {

    String nickname;
    int id;
    int punkt;
    public List<Karte> handkarte;

    public Spieler() {
    }

    public String getNickname() {
        return nickname;
    }

    public void karteLegen(Karte k) {
    }

    public Karte zugBeenden() {

        return null;
    }
}
