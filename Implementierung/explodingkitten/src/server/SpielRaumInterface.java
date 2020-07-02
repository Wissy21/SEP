package server;

import gui.controller.ILobbyObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SpielRaumInterface extends Remote {
    public void sendMessage(String msg , String time ,String benutzername) throws RemoteException;
    public ArrayList<Nachricht> getMessage() throws RemoteException;
    public void registerObserver(String userName, ILobbyObserver io) throws RemoteException;
}
