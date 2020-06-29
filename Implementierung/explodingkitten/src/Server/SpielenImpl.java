package Server;



import Server.datenbankmanager.DBmanager;

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
