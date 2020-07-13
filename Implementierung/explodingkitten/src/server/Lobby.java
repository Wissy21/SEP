package server;

import gui.controller.ILobbyObserver;
import gui.controller.LobbyController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;


public class Lobby extends UnicastRemoteObject implements LobbyInterface  {

    SpielChat chat;
    public ArrayList<SpielRaum> listeSpielraeume;
    public HashMap<String, ILobbyObserver> userLobserverMap;

    /**
     * Konstruktor von der Lobby
     * @throws RemoteException die Exception wird zurückgegeben, wenn es einen Fehler mit der Verbindung gibt
     */
    public Lobby() throws RemoteException {
        this.chat = new SpielChat();
        userLobserverMap = new HashMap<String, ILobbyObserver>();
    }

    public void spielraumBeitreten(SpielRaum s) {
    }

    public SpielRaum spielraum_erstellen() {
        return null;
    }

    /**
     * die Methode sendet eine Nachricht in der Lobby
     * @param msg die Nachricht
     * @param time die Uhrzeit, zu der die Nachricht gesendet wurde
     * @param benutzername name der sender
     */
    public void sendMessage(String msg , String time ,String benutzername){
        chat.nachrichSchreiben(msg , time ,benutzername);

        for(String nom : userLobserverMap.keySet()){
            ILobbyObserver current = userLobserverMap.get(nom);
            try {
                current.updateMessageList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * die Methode gibt die List von Nachrichten zurück
     * @return List von Nachrichten
     */
    public ArrayList<Nachricht> getMessage(){
        return chat.nachrichten;
    }

    /**
     * die Methode fügt einen User und seinen Observer in der Map
     * @param userName name des Users
     * @param io Observer
     */
    public void registerObserver(String userName, ILobbyObserver io){
        userLobserverMap.put(userName, io);
    }
}
