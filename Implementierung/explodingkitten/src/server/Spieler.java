package server;

import exceptions.NotYourRundeException;
import server.karten.Karte;

import java.util.List;

public interface Spieler {
    public void karteLegen(Karte k) throws NotYourRundeException;
    public void zugBeenden() throws NotYourRundeException;
    public void addKarte(Karte karte);
    public void removeKarte(Karte karte);
    public String getname();

}
