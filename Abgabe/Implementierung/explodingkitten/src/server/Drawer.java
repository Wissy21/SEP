package server;

import exceptions.NotYourRundeException;
import server.karten.Karte;

public class Drawer implements Runnable{
    String username;
    SpielRaum room;

    /**
     * Thread der für das ziehen der Karten zuständig ist
     *
     * @param username  Name des Spielers, der eine Karte ziehen will
     * @param s Raum in dem das Kartenziehen stattfindet
     */
    public Drawer(String username, SpielRaum s) {
        room = s;
        this.username = username;
    }

    /**
     * Thread zieht eine Karte.
     * Wenn es ein Exploding Kitten ist wartet er 15 Sekunden auf den Spieler, welcher Zeit hat zu entschärfen
     * Wenn die Zeit vorbei ist wird geprüft ob der Spieler erfogreich war.
     * Wenn nein wird er aus der Reihenfolge entfernt und uns aus dem Raum geworfen
     * In allen anderen fällenm geht es mit dem nächsten Spieler weiter
     */
    @Override
    public void run() {

        Karte k = room.spielstapel.pop();
        room.sendMessage(username+" zieht.","","Serveradmin");
        if(k.getEffekt().equals("ExplodingKitten")){
            room.setExpolding(true);
            room.explKitten = k;
            if(room.current.isBot) {
                room.current.entschaerfen(room,k);
            } else {
                room.notify(username, "Exploding", k);
            }

            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                System.out.println("Drawer closed.");
            }
            if(room.isExpolding()) {
                if(room.current.isBot) {
                    room.explodiert(username,k);
                } else {
                    room.notify(username, "Ausgeschieden", k);
                }
            } else {
                room.notify(username,"Position",null);
                room.spielstapel.insertElementAt(k,room.getPosition());
            }
            room.explKitten = null;
            room.setPosition(0);
        } else {
            room.current.handkarte.add(k);
            room.notify(username,"Bekommen",k);
        }
        room.naechsterSpieler();
    }
}
