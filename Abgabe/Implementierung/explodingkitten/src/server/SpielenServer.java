package server;

import server.datenbankmanager.DBinterface;
import server.datenbankmanager.DBmanager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

public class SpielenServer {

    /**
     * Startet den Server und hinterlegt die Datenbank und die Lobby für RMI
     * @param args Eingabeparameter
     */
    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(1900);
            DBinterface db =  new DBmanager();
            LobbyInterface lb =  new Lobby();

            try {
                Naming.rebind("rmi://localhost:1900/db", db);
                Naming.rebind("rmi://localhost:1900/lobby", lb);

                System.out.println("Server started");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
