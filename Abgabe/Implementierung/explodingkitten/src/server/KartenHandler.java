package server;

import server.karten.Karte;

import java.util.Collections;

public class KartenHandler implements Runnable{

    String ausgabe;
    String effekt;
    SpielRaum room;
    Karte gespielt;
    String user;


    /**
     * Thread der für die Ausführung der Karte zuständig ist
     *
     * @param k Karte die auszuführen ist
     * @param raum  Spielraum in dem die Karte gespielt wurde
     */
    public KartenHandler(Karte k,SpielRaum raum, String user) {

        gespielt = k;
        this.effekt = k.getEffekt();
        this.room = raum;
        this.user = user;
    }

    /**
     * Methode die den Karteneffekt ausführt:
     * Anfangs wird die karte immer aus der Hand auf den Ablagestapel gelegt
     * Die anderen Spieler werden dann informiert das die Karte gelegt wurde, um eventuell mit Nö! zu antworten
     * Danach wird 5 Sekunden gewartet
     * Je nach der Nö-Situation wird der Effekt der gespielten Karte dann ausgeführt oder nicht.
     *  Angriff: Der nächste Spieler muss zweimal ziehen. Beendet den Zug
     *  Hops: Überspringt das ziehen einer Karte. Beendet den Zug
     *  Wunsch: Wählt einen SPieler aus, der dir eine Karte geben soll
     *  Mischen: Mischt den Spielstapel
     *  BlickInDieZukunft: Zeigt dir die drei obersten Karten des Spielstapels
     *  Katzen-Karten: Kein Effekt
     * Wenn der Effekt geblockt wurde wird das danach noch signalisiert
     */
    @Override
    public void run() {

        room.current.handkarte.remove(gespielt);
        room.ablagestapel.push(gespielt);

        try {
            room.notify(user, "AbgelegtDu", gespielt);
            room.notifyEverybody("Abgelegt", gespielt);
            room.setNoe(false);
            room.sendMessage(user+" hat " + effekt+" gespielt.","","Serveradmin");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Handler closed.");
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
                    room.selectSpieler();
                    break;

                case "Mischen":
                    Collections.shuffle(room.spielstapel);
                    break;

                case "BlickInDieZukunft":
                    Karte k1 = room.spielstapel.pop();
                    Karte k2 = room.spielstapel.pop();
                    Karte k3 = room.spielstapel.pop();
                    ausgabe = k1.getEffekt() + "," + k2.getEffekt() + "," + k3.getEffekt();
                    if(room.getCurrent().isBot) {
                        room.getCurrent().naechsteKarte(k1.getEffekt());
                    }else {
                        room.notify(room.getCurrent().getNickname(), ausgabe, null);
                    }
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
            room.sendMessage(effekt+" wurde durch Nö! aufgehalten.","","Serveradmin");
            room.setNoe(false);
        }
    }
}
