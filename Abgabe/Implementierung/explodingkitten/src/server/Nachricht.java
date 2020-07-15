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

    /**
     * Überschriebene equals Methode zum Verlgeichen
     * @param obj Vergleichsobjekt
     * @return true wenn gleiche message sender und time, sonst false
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Nachricht)) {
            return false;
        }
        Nachricht n = (Nachricht) obj;
        return n.message.equals(message)&&n.sender.equals(sender)&&n.time.equals(time);
    }
}
