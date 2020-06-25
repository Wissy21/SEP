package main.server;

<<<<<<< HEAD
import main.server.datenbankmanager.DBmanager;
=======
import main.datenbankmanager.DBmanager;
>>>>>>> maxime

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SpielenImpl extends UnicastRemoteObject implements Spielen {

    public Lobby lobby;
    public DBmanager dbmanager;

    protected SpielenImpl(int port) throws RemoteException {
        super(port);
    }

    @Override
    public void start() {

    }
}
