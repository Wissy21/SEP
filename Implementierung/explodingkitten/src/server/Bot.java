package server;

import exceptions.NotYourRundeException;
import server.karten.Karte;
import java.util.ArrayList;

public class Bot implements Spieler {

    public String name;
    public ArrayList<Karte> handkarte;
    public SpielRaum spielraum;

    /**
     * Der Konstruktor von Objekten der Klasse
     * @param n ist der id des Bots
     */
    public Bot(int n){
        this.name = "Bot" + n;
        this.handkarte = new ArrayList<>();
    }

    /**
     * Spielstrategie von dem Bot
     * @throws NotYourRundeException die Exception wird zurückgegeben, wenn ein Spieler nicht in seiner Runde eine Karte legt
     */
    public void spielen() throws NotYourRundeException {
        if (handkarte.isEmpty()){
            zugBeenden();
        }
        else{
            Karte c = spielraum.ablagestapel.peek();
            Karte k0 = karteSuche("Noe");
            if(c.getEffekt().equals("Wunsch") || c.getEffekt().equals("BlickInDieZukunft") || c.getEffekt().equals("Hops") || c.getEffekt().equals("Mischen") || c.getEffekt().equals("Noe") || c.getEffekt().equals("Angriff")){
                if(!k0.equals(null)){
                    karteLegen(k0);
                }
                zugBeenden();
            }
            else{
                Karte k1 = karteSuche("Hops");
                if(!k1.equals(null)){
                    karteLegen(k1);
                }
                else{
                    Karte k2 = karteSuche("Angriff");
                    if(!k2.equals(null)){
                        karteLegen(k2);
                    }
                    else {
                        Karte k3 = karteSuche("Wunsch");
                        if(!k3.equals(null)){
                            karteLegen(k3);
                        }
                        else{
                            Karte k4 = karteSuche("BlickInDieZukunft");
                            if(!k4.equals(null)){
                                karteLegen(k4);
                                Karte c1 = spielraum.spielstapel.peek();
                                if(c1.getEffekt().equals("ExplodingKitten")){
                                    Karte k5 = karteSuche("Entschaerfung");
                                    if(!k5.equals(null)){
                                        karteLegen(k5);
                                    }
                                }
                                zugBeenden();
                            }
                            else{
                                Karte k6 = karteSuche("Mischen");
                                if(!k6.equals(null)){
                                    karteLegen(k6);
                                    zugBeenden();
                                }
                                else{
                                    Karte k8 = handkarte.get(0);
                                    karteLegen(k8);
                                    zugBeenden();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Methode gibt eine Karte mit dem gegebenen Effekt zurück, wenn es eine gibt
     * @param k der Effekt der gesuchte Karte
     * @return die gesuchte Karte oder null,wenn die Karte nicht gefunden wurde
     */
    public Karte karteSuche(String k){
        int i=0;
        Karte tmp = null;
        if(!handkarte.isEmpty()){
            while ( i < handkarte.size()){
                tmp = handkarte.get(i);
                if(tmp.getEffekt().equals(k)){
                    i = handkarte.size();
                }
                else{
                    tmp = null;
                    i++;
                }
            }
        }

        return tmp;
    }

    @Override
    public void karteLegen(Karte k) throws NotYourRundeException {
        spielraum.karteLegen(name, k);
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
