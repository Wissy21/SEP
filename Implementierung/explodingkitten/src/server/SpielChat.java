package server;

import java.util.ArrayList;

public class SpielChat {
    public ArrayList<Nachricht> nachrichten;

    /**
     * Konstruktor von Objekten
     */
    public SpielChat() {
        nachrichten = new ArrayList<Nachricht>();
    }

    /**
     * die Methode erstellt eine neue Nachricht
     * @param msg Nachricht
     * @param time Uhrzeit
     * @param benutzername Name des Senders
     */
    public void nachrichSchreiben(String msg , String time ,String benutzername) {
        Nachricht n = new Nachricht(msg, benutzername, time);
        nachrichten.add(n);
    }
}
