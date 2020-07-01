package server;

import java.io.Serializable;

public class Nachricht implements Serializable {

    public String message;
    public String sender;
    public String time;

    public Nachricht(String m, String s, String t) {
        message = m;
        sender = s;
        time= t;
    }
}
