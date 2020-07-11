package server;

import gui.controller.ILobbyObserver;
import gui.controller.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lobby extends UnicastRemoteObject implements LobbyInterface  {
    SpielChat chat;

    public HashMap<String, ILobbyObserver> userLobserverMap;
    public ArrayList<String> spielraume;

    public Lobby() throws RemoteException {
        this.chat = new SpielChat();
        userLobserverMap = new HashMap<String, ILobbyObserver>();
    }

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

    public ArrayList<Nachricht> getMessage(){
        return chat.nachrichten;
    }

    public void registerObserver(String userName, ILobbyObserver io){
        userLobserverMap.put(userName, io);
    }

    @Override
    public void addroom(String raumname) {
        spielraume.add(raumname);
        for(String nom : userLobserverMap.keySet()){
            ILobbyObserver current = userLobserverMap.get(nom);
            try {
                current.updateMessageList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> getRooms() throws RemoteException {
        return spielraume;
    }
}
