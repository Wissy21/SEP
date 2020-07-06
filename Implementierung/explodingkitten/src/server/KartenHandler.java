package server;

import server.karten.Karte;

import java.util.Collections;

public class KartenHandler implements Runnable{

    String ausgabe;
    String effekt;
    SpielRaum room;
    Karte gespielt;

    public KartenHandler(Karte k,SpielRaum raum) {

        gespielt = k;
        this.effekt = k.getEffekt();
        this.room = raum;
    }

    @Override
    public void run() {

        //TODO Anzeigen welche Karte gespielt wurde

        if(effekt.equals("Noe")) {
            room.changeNoe();
        }
        else if (effekt.equals("Entschaerfung")) {
             room.setExpolding(false);
             room.spielstapel.insertElementAt(room.explKitten, room.getPosition());
             room.naechsterSpieler();
        } else {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!room.isNoe()) {
                switch (effekt) {
                    case "Angriff":
                        if (room.isAngriff()) {
                            room.setAngriff(false);
                        }
                        room.naechsterSpieler();
                        room.setAngriff(true);
                        break;
                    case "Hops":
                        room.naechsterSpieler();
                        break;

                    case "Wunsch":
                        //TODO Aufruf Client Methode zum auswählen eines Ziels und dann Auswahl der zu gebenden Karte
                        break;
                    case "Mischen":
                        Collections.shuffle(room.spielstapel);
                        break;
                    case "BlickInDieZukunft":
                        Karte k1 = room.spielstapel.pop();
                        Karte k2 = room.spielstapel.pop();
                        Karte k3 = room.spielstapel.pop();
                        ausgabe = k1.getEffekt() + "," + k2.getEffekt() + "," + k3.getEffekt();
                        //TODO ausgabe an Client von current senden
                        room.spielstapel.push(k3);
                        room.spielstapel.push(k2);
                        room.spielstapel.push(k1);
                        break;
                    case "Katze1":
                    case "Katze2":
                    case "Katze3":
                    case "Katze4":
                    case "Katze5":
                        break;
                }
            } else {
                room.setNoe(false);
            }
        }
        room.current.handkarte.remove(gespielt);
        room.ablagestapel.add(gespielt);
    }
}
