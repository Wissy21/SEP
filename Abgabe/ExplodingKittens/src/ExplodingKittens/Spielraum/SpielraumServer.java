package ExplodingKittens.Spielraum;

import ExplodingKittens.Bestenliste.BestenlisteServer;


import java.rmi.RemoteException;

public interface SpielraumServer extends BestenlisteServer {

    void deleteRoom(Spielraum room) throws RemoteException;

    void updateRoom(String oldroom, Spielraum newroom) throws  RemoteException;


}
