package main.client.gui.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILobbyObserver extends Remote {
    void updateMessageList() throws RemoteException;
    void updateRaumList() throws RemoteException;
}
