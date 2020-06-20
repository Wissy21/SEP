package ExplodingKittens.Spielraum;

import ExplodingKittens.Bestenliste.BestenlisteServer;

import java.rmi.RemoteException;

/**
 * Interface das die Methoden des Spieraums für den Server zur verfügung stellt
 */
public interface SpielraumServer extends BestenlisteServer {

    /**
     * Methode um einen Raum aufzulösen
     *
     * @param room              Raum der aufgelöst werden soll
     * @throws RemoteException  Fehler bei der Datenübertragung
     */
    void deleteRoom(Spielraum room) throws RemoteException;

    /**
     * Metthode um einen Raum zu aktualisieren
     *
     * @param oldroom           Name des alten Raums
     * @param newroom           Neuer Raum
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    void updateRoom(String oldroom, Spielraum newroom) throws  RemoteException;


}
