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

    public Spieler(boolean isBot) {
        this.isBot = isBot;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<Karte> getHandkarte() {
        return handkarte;
    }

    @Override
    public String toString() {
        return nickname;
    }


    public void zugDurchfuehren(SpielRaum raum){
        try{
            for(Karte k : handkarte) {
                if(k.getEffekt().equals("BlickInDieZukunft")) {
                    raum.karteLegen(nickname,k);
                    break;
                }
            }
            Thread.sleep(6000);

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
            if(k.getEffekt().equals("Entschaerfen")) {
                try {
                    raum.karteLegen(nickname,k);
                    raum.notifyEverybody("Abgelegt",k);
                    Random r = new Random();
                    raum.setPosition(r.nextInt(raum.spielstapel.size()));
                    break;
                } catch (NotYourRundeException | NoExplodingKittenException e) {
                    e.printStackTrace();
                }
            }
        }
        raum.notifyEverybody("Abgelegt",kitten);
        raum.notifyEverybody("Raus",new Karte("",nickname));
    }

    public void explodiert(SpielRaum raum) {
        for(Karte k: handkarte) {
            raum.ablagestapel.push(k);
            raum.notifyEverybody("Abgelegt",k);
        }
        raum.explodiert(nickname);

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
