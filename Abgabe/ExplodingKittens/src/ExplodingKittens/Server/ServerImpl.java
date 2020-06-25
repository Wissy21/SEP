package ExplodingKittens.Server;

import ExplodingKittens.Exceptions.*;
import ExplodingKittens.Spielraum.Spielraum;
import ExplodingKittens.User.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse die den Server implementiert
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    /**
     * accounts         Hier sind Benutzername und Passwort von jedem Benutzer gespeichert
     * online           Hier sind alle Nutzer die in der Lobby sind aufgelistet
     * rooms            Hier sind alle Spielraüme aufgelistet
     * bestenlsiste     Hier sind Benutzername und Anzahl der Siege gespeichert
     */
    private HashMap<String,String> accounts = new HashMap<>();
    private ArrayList<User> online = new ArrayList<>();
    private HashMap<String,Spielraum> rooms = new HashMap<>();
    private HashMap<String, Integer> bestenliste = new HashMap<>();

    /**
     * Konstruktor für den Server
     *
     * @throws RemoteException  Fehler bei Datenübertrtagung
     */
    public ServerImpl() throws RemoteException {}


    /**
     * Methode die den Server startet und ihn in der Registry hinterlegt
     *
     * @param args              Ungenutzte Eingabeparameter
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        ServerImpl server = new ServerImpl();
        reg.rebind("login-server",server);
    }

    /**
     * Siehe LoginServer
     *
     * HashMap wird nach Benutzername und Passwort Kombination durchsucht
     * Bei erfolgreichem anmelden werden die Daten an die Lobby weitergegeben und der Nutzer ist angemeldet
     */
    @Override
    public void login(String name, String password) throws WrongPasswordException, UserNotExistException {
        for (Map.Entry<String,String> e: accounts.entrySet()) {
            if(name.equals(e.getKey())){
                if(password.equals(e.getValue())) {
                    addUserLobby(new User(name));
                    return;
                }
                throw new WrongPasswordException();
            }
        }
        throw new UserNotExistException();
    }

    /**
     * Siehe LoginServer
     *
     * HashMap wird durchsucht, damit sich der Benutzername nicht doppelt
     * Wenn dem nicht der Fall ist werden die Daten in die HashMap aufgenommen und der Nutzer in die Lobby weitergeleitet
     */
    @Override
    public void register(String name, String password) throws UsernameTakenException {
        for (String s: accounts.keySet()) {
            if(name.equals(s)){
                throw new UsernameTakenException();
            }
        }
        accounts.put(name, password);
        addUserLobby(new User(name));
    }

    /**
     * Siehe LobbyServer
     *
     * @param user Benutzer der hinzugefügt werden soll
     */
    @Override
    public void addUserLobby(User user) {
        online.add(user);
        addUserListe(user);
    }

    /**
     * Siehe LobbyServer
     *
     * @param user              Benutzer der entfernt werden soll
     */
    @Override
    public void removeUser(User user) {
        online.remove(user);
    }


    /**
     * Siehe LobbyServer
     *
     * Neuer Raum wird erstellt und der Liste der Räume hinzugefügt
     * dann wird der erstellende Spieler inm den Raum bewegt und sein Status geupdatet
     *
     * @param user                      Benutzer der den Raum erstellen will
     * @param name                      Name für den Raum
     * @return                          Raum der Erstellt wurde
     * @throws RoomIsFullException      Fehler wenn Raum bereits voll ist
     * @throws RoomNameTakenException   Fehler wenn der Name bereits benutzt wird
     * @throws NoInputException         Fehler bei der Namenseingabe
     */
    @Override
    public Spielraum createRoom(User user, String name) throws RoomIsFullException, RoomNameTakenException, NoInputException {
        Spielraum room = new Spielraum(user,name);
        if(name == null) {
            throw new NoInputException();
        }
        for (String s: rooms.keySet()) {
            if(s.equals(name)) {
                throw new RoomNameTakenException();
            }
        }
        rooms.put(name,room);
        room.addPlayer(user);
        user.setInGame(true);
        user.setRoom(room);
        return room;
    }

    /**
     * Siehe LobbyServer
     *
     * Spieler wird dem Raum hinzugefügt und sein Status geupdatet
     *
     * @param user                  Benutzer der dem Raum beitreten will
     * @param room                  Raum den beigetreten werden soll
     * @throws RoomIsFullException  Fehler wenn Raum bereits voll ist
     */
    @Override
    public void enterRoom(User user, Spielraum room) throws RoomIsFullException {
        room.addPlayer(user);
        user.setInGame(true);
        user.setRoom(room);
    }

    /**
     * Siehe LobbyServer
     *
     * Des Value des übergebenen Spielers wird um 1 erhöht
     *
     * @param user              Benutzer der das Spiel gewonnen hat
     */
    @Override
    public void addVictory(User user) {
        bestenliste.put(user.getName(),bestenliste.get(user.getName())+1);
    }

    /**
     * Siehe LobbyServer
     *
     * Spieler wird nur hinzugefügt wenn er noch nicht enthalten ist
     *
     * @param user              Spieler der hinzugefügt werden soll
     */
    @Override
    public void addUserListe(User user) {
        if(!bestenliste.containsKey(user.getName())) {
            bestenliste.put(user.getName(), 0);
        }
    }

    @Override
    public HashMap<String,Integer> getBestenliste() {
        return bestenliste;
    }

    @Override
    public HashMap<String,Spielraum> getRooms() {
        return rooms;
    }

    /**
     * Methode die einen Raum entfernt, der leer ist
     *
     * @param room  Raum der zu entfernen ist
     */
    public void deleteRoom(Spielraum room) {
        rooms.remove(room.getName());
    }

    /**
     * Methode die die Raum Datenbank updatet, wenn sich an einem Raum etwas ändert
     *
     * @param oldroom   Name des alten Raums
     * @param newroom   Neuer Raum
     */
    @Override
    public void updateRoom(String oldroom, Spielraum newroom) {
        rooms.remove(oldroom);
        rooms.put(newroom.getName(),newroom);
    }

}
