package gui.controller;

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
}
