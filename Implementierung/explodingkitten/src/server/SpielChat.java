package server;

import java.util.ArrayList;
import java.util.List;

public class SpielChat {
    public ArrayList<Nachricht> nachrichten;

    public SpielChat() {
        nachrichten = new ArrayList<Nachricht>();
    }

    public void nachrichSchreiben(String msg , String time ,String benutzername) {
        Nachricht n = new Nachricht(msg, benutzername, time);
        nachrichten.add(n);
    }
}
