package main.client.gui.controller;

import main.server.karten.Karte;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRaumObserver extends Remote {

    void updateMessageList() throws RemoteException;

    void notify (String spielername, String message, Karte k) throws RemoteException;
}
