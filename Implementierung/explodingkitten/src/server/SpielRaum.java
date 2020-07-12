package server;

import gui.controller.ILobbyObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class SpielRaum extends UnicastRemoteObject implements LobbyInterface {
    public String name;
    public int anzahlSpieler;
    public Queue<server.Spieler> spielers;
    public server.SpielStapel spielstapel;
    public server.AblageStapel ablagestapel;
    public server.SpielChat raumChat;

    SpielChat chat;
    public HashMap<String, ILobbyObserver> userLobserverMap;

    public server.SpielChat lobbyChat;

    public SpielRaum() throws RemoteException {
        this.chat = new SpielChat();
        userLobserverMap = new HashMap<String, ILobbyObserver>();
    }

    public void botHinzufuegen() {
    }

    public void spielStarten() {
    }

    public void spielRaumSchliessen() {
    }

    public server.Spieler setCurrentSpieler() {

        return null;
    }

    @Override
    public void sendMessage(String msg, String time, String benutzername) throws RemoteException {

        chat.nachrichSchreiben(msg, time, benutzername);

        for (String name : userLobserverMap.keySet()) {
            ILobbyObserver current = userLobserverMap.get(name);
            try {
                current.updateMessageList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Nachricht> getMessage() throws RemoteException {
        return chat.nachrichten;
    }

    @Override
    public void registerObserver(String userName, ILobbyObserver io) throws RemoteException {
        userLobserverMap.put(userName, io);
    }
}
