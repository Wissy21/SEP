package Server;

import Server.datenbankmanager.DBinterface;
import Server.datenbankmanager.DBmanager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class SpielenServer {

    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(1900);
            DBinterface db = (DBinterface) new DBmanager();
            try {
                Naming.rebind("rmi://localhost:1900/db", db);

                System.out.println("Server started");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
