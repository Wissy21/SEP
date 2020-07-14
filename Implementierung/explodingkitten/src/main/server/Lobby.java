package main.server;

import main.gui.controller.ILobbyObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby extends UnicastRemoteObject implements LobbyInterface  {
    SpielChat chat;

    public HashMap<String, ILobbyObserver> userLobserverMap;
    public ArrayList<String> spielraume;

    /**
     * Erstellt eine neue Lobby mit neuem Chat, einer Liste für alle Nutzer in der Lobby und eine Liste aller Spielräume
     * @throws RemoteException Fehler bei RMI
     */
    public Lobby() throws RemoteException {
        this.chat = new SpielChat();
        userLobserverMap = new HashMap<String, ILobbyObserver>();
        spielraume = new ArrayList<String>();
    }

    /**
     * Bekommt die Inhalte einer Nachricht und sendete diese an alle Spieler in der Lobby
     * @param msg Inhalt der Nachricht
     * @param time Zeitstempel
     * @param benutzername Name des Senders
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
     * @return Gibt eine Liste aller nachrichten zurück
     */
    public ArrayList<Nachricht> getMessage(){
        return chat.nachrichten;
    }

    /**
     * Registriert einen Nutzer in der Lobny ,sodass er alle Veränderungen mitbekommt
     * @param userName Nmae des Nutzers
     * @param io Observer des die Veränderungen überbringt
     */
    public void registerObserver(String userName, ILobbyObserver io){
        userLobserverMap.put(userName, io);
    }

    /**
     * Fügt der Lobby einen neuen Raum hinzu und benachrichtigt alle anderen Clients das es diesen Raum jetzt gibt
     * @param raumname Name des Raums
     */
    @Override
    public void addroom(String raumname) {
        spielraume.add(raumname);
        for(String nom : userLobserverMap.keySet()){
            ILobbyObserver current = userLobserverMap.get(nom);
            try {
                current.updateRaumList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> getRooms() {
        return spielraume;
    }
}
