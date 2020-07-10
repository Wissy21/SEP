package server;

import exceptions.NichtGenugSpielerException;
import exceptions.NoExplodingKittenException;
import exceptions.NotYourRundeException;
import exceptions.SpielraumVollException;
import gui.controller.IRaumObserver;
import server.karten.Karte;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SpielRaumInterface extends Remote {

    void sendMessage(String msg , String time ,String benutzername) throws RemoteException;

    ArrayList<Nachricht> getMessage() throws RemoteException;

    void registerObserver(String userName, IRaumObserver io) throws RemoteException;

    void betreten(String name) throws RemoteException;

    void spielraumVerlassen(String spielername) throws RemoteException;

    void botHinzufuegen() throws RemoteException, SpielraumVollException;

    void spielStarten() throws RemoteException, NichtGenugSpielerException;

    void karteLegen(String username, Karte k) throws RemoteException, NotYourRundeException, NoExplodingKittenException;

    void naechsterSpieler() throws RemoteException;

    void zugBeenden(String spielername) throws RemoteException, NotYourRundeException;

    String amZug() throws RemoteException;

    ArrayList<Spieler> getSpieler() throws RemoteException;

    String ping() throws RemoteException;

    boolean isRunning() throws RemoteException;

    Spieler getCurrent() throws RemoteException;

    void setAusgewaehler(Spieler s) throws RemoteException;

    void abgeben(String name, Karte k) throws RemoteException;
}
