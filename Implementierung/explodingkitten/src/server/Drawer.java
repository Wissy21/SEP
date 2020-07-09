package server;

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
            room.current.handkarte.add(k);

            if(k.getEffekt().equals("ExplodingKitten")){
                room.setExpolding(true);
                room.explKitten = k;

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(room.isExpolding()) {
                    room.ablagestapel.addAll(room.current.handkarte);
                    room.ablagestapel.add(k);
                    room.spielraumVerlassen(username);
                    room.reihenfolge.remove();
                }
            }
            room.naechsterSpieler();
    }
}
