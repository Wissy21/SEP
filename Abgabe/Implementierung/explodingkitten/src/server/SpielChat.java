package server;

import java.util.ArrayList;

public class SpielChat {
    public ArrayList<Nachricht> nachrichten;

    /**
     * Erstellt einen neuen Chat
     */
    public SpielChat() {
        nachrichten = new ArrayList<>();
    }

    /**
     * Fügt dem Chat eine neue Nachricht hinzu
     * @param msg Inhalt
     * @param time Zeitstempel
     * @param benutzername Sender
     */
    public void nachrichSchreiben(String msg , String time ,String benutzername) {
        Nachricht n = new Nachricht(msg, benutzername, time);
        nachrichten.add(n);
    }
}
