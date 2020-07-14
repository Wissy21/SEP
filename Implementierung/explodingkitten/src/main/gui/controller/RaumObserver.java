package main.gui.controller;

import main.server.karten.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RaumObserver extends UnicastRemoteObject implements IRaumObserver {
    IRaumObserver raum;

    /**
     * Erstellt einen neuen Raumobserver für den angegebenen Spielraum
     *
     * @param sc Spielraum der observiert wird
     * @throws RemoteException Fehler bei RMI
     */
    RaumObserver(SpielraumController sc) throws RemoteException {
        raum = sc;
    }

    /**
     * Frischt die Nachrichten auf, sodass alle angezeigt werden
     *
     * @throws RemoteException Fehler bei RMI
     */
    @Override
    public void updateMessageList() throws RemoteException {
        raum.updateMessageList();
    }

    /**
     * Kommunikation zwischen Server und Client
     *
     * @param spielername Name des angesprochenen Spielers
     * @param message     Nachricht an den Spieler
     * @param k           optionale Karte die gesendet werden soll
     * @throws RemoteException Fehler bei RMI
     */
    @Override
    public void notify(String spielername, String message, Karte k) throws RemoteException {
        raum.notify(spielername, message, k);
    }
}
