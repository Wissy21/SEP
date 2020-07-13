package server;

import java.io.Serializable;

public class Nachricht implements Serializable {

    public String message;
    public String sender;
    public String time;

    /**
     * Konstruktor von Objekten der Klasse
     * @param m Nachricht
     * @param s Name des Senders
     * @param t Uhrzeit
     */
    public Nachricht(String m, String s, String t) {
        message = m;
        sender = s;
        time= t;
    }
}
