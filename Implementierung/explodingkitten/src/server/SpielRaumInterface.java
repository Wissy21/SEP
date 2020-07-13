package server;

import exceptions.*;
import gui.controller.IRaumObserver;
import server.karten.Karte;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SpielRaumInterface extends Remote {

    void sendMessage(String msg , String time ,String benutzername) throws RemoteException;

    ArrayList<Nachricht> getMessage() throws RemoteException;

    void registerObserver(String userName, IRaumObserver io) throws RemoteException;

    void betreten(String name) throws RemoteException, SpielLauftBereitsException;

    boolean spielraumVerlassen(String spielername) throws RemoteException;

    void botHinzufuegen() throws RemoteException, SpielraumVollException;

    void spielStarten() throws RemoteException, NichtGenugSpielerException;

    void karteLegen(String username, Karte k) throws RemoteException, NotYourRundeException, NoExplodingKittenException;

    void naechsterSpieler() throws RemoteException;

    void zugBeenden(String spielername) throws RemoteException, NotYourRundeException;

    String amZug() throws RemoteException;

    ArrayList<Spieler> getSpieler() throws RemoteException;

    boolean isRunning() throws RemoteException;

    Spieler getCurrent() throws RemoteException;

    void setAusgewaehler(Spieler s) throws RemoteException;

    void abgeben(String name, Karte k) throws RemoteException;

    int getStapelSize() throws RemoteException;

    void setPosition(int pos) throws RemoteException;

    void explodiert(String spielername) throws RemoteException;
}

