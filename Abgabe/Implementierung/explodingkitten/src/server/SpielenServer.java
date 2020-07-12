package server;

import server.datenbankmanager.DBinterface;
import server.datenbankmanager.DBmanager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class SpielenServer {

    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(1900);
            DBinterface db = (DBinterface) new DBmanager();
            LobbyInterface lb = (LobbyInterface) new Lobby();

            try {
                Naming.rebind("rmi://localhost:1900/db", db);
                Naming.rebind("rmi://localhost:1900/lobby", lb);

                System.out.println("Server started");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
