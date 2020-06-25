package main.server;

<<<<<<< HEAD
import main.server.datenbankmanager.DBmanager;
=======
import main.datenbankmanager.DBmanager;
>>>>>>> maxime

import java.rmi.Remote;

public interface Spielen extends Remote {

     Lobby lobby = null;
     DBmanager dbmanager = null;

     void start();


}
