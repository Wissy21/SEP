package main.client.gui.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LobbyObserver extends UnicastRemoteObject implements ILobbyObserver{
    ILobbyObserver lobby;

    LobbyObserver(LobbyController lc) throws RemoteException{
        lobby = lc;
    }

    @Override
    public void updateMessageList() throws RemoteException {
        lobby.updateMessageList();
    }

    @Override
    public void updateRaumList() throws RemoteException {
        lobby.updateRaumList();
    }
}
