package Server;

import Server.datenbankmanager.DBmanager;

import java.rmi.Remote;


public interface Spielen extends Remote {
     void start();
}
