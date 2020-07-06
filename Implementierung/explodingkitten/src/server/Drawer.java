package server;

import server.karten.Karte;

public class Drawer implements Runnable{
    String username;
    SpielRaum room;

    public Drawer(String username, SpielRaum s) {
        room = s;
        this.username = username;
    }

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
