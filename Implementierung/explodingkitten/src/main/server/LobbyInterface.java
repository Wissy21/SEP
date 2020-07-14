package main.server;

import main.gui.controller.ILobbyObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LobbyInterface extends Remote {
    void sendMessage(String msg , String time ,String benutzername) throws RemoteException;
    ArrayList<Nachricht> getMessage() throws RemoteException;
    void registerObserver(String userName, ILobbyObserver io) throws RemoteException;
    void addroom(String raumname) throws RemoteException;
    ArrayList<String> getRooms() throws RemoteException;
}
