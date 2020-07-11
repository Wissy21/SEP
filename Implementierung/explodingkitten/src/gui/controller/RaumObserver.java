package gui.controller;

import server.karten.Karte;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RaumObserver extends UnicastRemoteObject implements IRaumObserver {
    IRaumObserver raum;

    RaumObserver(SpielraumController sc) throws RemoteException {
        raum = sc;
    }

    @Override
    public void updateMessageList() throws RemoteException {
        raum.updateMessageList();
    }

    @Override
    public void notify(String spielername, String message, Karte k) throws RemoteException {
        raum.notify(spielername, message, k);
    }
}
