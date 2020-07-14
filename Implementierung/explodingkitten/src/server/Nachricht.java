package server;

import java.io.Serializable;

public class Nachricht implements Serializable {

    public String message;
    public String sender;
    public String time;

    /**
     * Erstellt eine neue Nachricht
     * @param m Inhalt
     * @param s Sender
     * @param t Zeitstempel
     */
    public Nachricht(String m, String s, String t) {
        message = m;
        sender = s;
        time= t;
    }
}
