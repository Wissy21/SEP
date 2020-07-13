package server;

import exceptions.NoExplodingKittenException;
import exceptions.NotYourRundeException;
import server.karten.Karte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Spieler implements Serializable {

    String nickname;
    public ArrayList<Karte> handkarte = new ArrayList<>();
    boolean isBot;
    String naechsteKarte="";

    /**
     * Erstellt einen neuen Spieler und gibt an, ob es sich um einen Bot handelt
     * @param isBot Bot oder nicht
     */
    public Spieler(boolean isBot) {
        this.isBot = isBot;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<Karte> getHandkarte() {
        return handkarte;
    }

    /**
     * Zur Vereinfachung der Anzeige
     * @return Gibt den Namen zurück
     */
    @Override
    public String toString() {
        return nickname;
    }


    /**
     * Zug des Bots wird durchgeführt
     * Wenn er einen BlickInDieZukunft hat spielt er diesen um einem möglichen Exploding Kitten auszuweichen
     * Danach beendet er seinen Zug
     * @param raum Raum in dem Der bot ist
     */
    public void zugDurchfuehren(SpielRaum raum){
        try{

            Thread.sleep(4000);
            for(Karte k : handkarte) {
                if(k.getEffekt().equals("BlickInDieZukunft")) {
                    raum.karteLegen(nickname,k);
                    break;
                }
            }
            Thread.sleep(4000);
            if(naechsteKarte.equals("ExplodingKitten")) {
                for(Karte k : handkarte) {
                    if(k.getEffekt().equals("Angriff")||k.getEffekt().equals("Hops")||k.getEffekt().equals("Mischen")) {
                        raum.karteLegen(nickname,k);
                        Thread.sleep(6000);

                        break;
                    }
                }
            }
            naechsteKarte = "";
            raum.zugBeenden(nickname);
        } catch (InterruptedException | NoExplodingKittenException | NotYourRundeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode für Wunsch
     * Gibt eine zufällige Karte ab
     * @param raum Spielraum in dem der Bot ist
     */
    public void abgeben(SpielRaum raum) {
        Random r = new Random();
        Karte abgabe = handkarte.get(r.nextInt(handkarte.size()));
        raum.abgeben(nickname,abgabe);
    }
    /**
     * Methode für Wunsch
     * Wählt einen zufälligen Spieler aus
     * @param raum Spielraum in dem der Bot ist
     */
    public void auswaehlen(SpielRaum raum) {
        Random r = new Random();
        Spieler victim = raum.getSpieler().get(r.nextInt(raum.anzahlSpieler));
        raum.setAusgewaehler(victim);
    }

    /**
     * Methode zum entschärfen eines Exploding Kittens
     * Wenn der Bot eine Entschärfung in der Hand hat spielt er diese, wenn nicht explodiert er
     * Wen er entschäft legt er das Exploding Kitten an eine zufällige Positin im Spielstapel zurück
     * @param raum Raum in dem der Bot ist
     */
    public void entschaerfen(SpielRaum raum) {
        for(Karte k : handkarte) {
            if(k.getEffekt().equals("Entschaerfung")) {
                try {
                    raum.karteLegen(nickname,k);
                    raum.notifyEverybody("Abgelegt",k);
                    Random r = new Random();
                    if(raum.spielstapel.size()==0) {
                        raum.setPosition(0);
                    } else {
                        raum.setPosition(r.nextInt(raum.spielstapel.size()));
                    }
                    break;
                } catch (NotYourRundeException | NoExplodingKittenException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Methode um die nächste Karte im Spielstapel zu speichern
     * @param effekt Effekt der nächsten Karte
     */
    public void naechsteKarte(String effekt) {
        naechsteKarte = effekt;
    }

    /**
     * Überschriebene equals Methode zum einfacheren Vergleichen
     * @param o Objekt zum vergleichen
     * @return Wenn name und bot-Status gleich sind true, sonst false
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Spieler)) {
            return false;
        }
        Spieler s = (Spieler) o;

        return s.getNickname().equals(nickname)&&(s.isBot==isBot);
    }

}
