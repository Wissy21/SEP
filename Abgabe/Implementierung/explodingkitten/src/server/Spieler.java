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

    public void abgeben(SpielRaum raum) {
        Random r = new Random();
        Karte abgabe = handkarte.get(r.nextInt(handkarte.size()));
        raum.abgeben(nickname,abgabe);
    }

    public void auswaehlen(SpielRaum raum) {
        Random r = new Random();
        Spieler victim = raum.getSpieler().get(r.nextInt(raum.anzahlSpieler));
        raum.setAusgewaehler(victim);
    }

    public void entschaerfen(SpielRaum raum,Karte kitten) {
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


    public void naechsteKarte(String effekt) {
        naechsteKarte = effekt;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Spieler)) {
            return false;
        }
        Spieler s = (Spieler) o;

        return s.getNickname().equals(nickname)&&(s.isBot==isBot);
    }

}
