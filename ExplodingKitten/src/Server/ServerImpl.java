package Server;

import Common.User;
import Exceptions.RoomIsFullException;
import Exceptions.UserNotExistException;
import Exceptions.UsernameTakenException;
import Exceptions.WrongPasswordException;

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
     * accounts         Hier sind Benutzername und Passwort gespeichert
     * online           Hier sind alle Nutzer die in der Lobby sind aufgelistet
     * rooms            Hier sind alle Spielraüme aufgelistet
     * bestenlsiste     Hier sind Benutzername und Anzahl der Siege gespeichert
     */
    private HashMap<String,String> accounts = new HashMap<>();
    private ArrayList<User> online = new ArrayList<>();
    private ArrayList<Spielraum> rooms = new ArrayList<>();
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
     * dann wird der erstellende Spieler inm den Raum bewegt und aus der Lobby entfernt
     *
     * @param user                  Benutzer der den Raum erstellen will
     * @param name                  Name für den Raum
     * @throws RoomIsFullException  Fehler wenn Raum bereits voll ist
     */
    @Override
    public void createRoom(User user, String name) throws RoomIsFullException {
        Spielraum room = new Spielraum(user,name);
        rooms.add(room);
        room.addPlayer(user);
        removeUser(user);
    }

    /**
     * Siehe LobbyServer
     *
     * Spieler wird dem Raum hinzugefügt und aus der Lobby entfernt
     *
     * @param user                  Benutzer der dem Raum beitreten will
     * @param room                  Raum den beigetreten werden soll
     * @throws RoomIsFullException  Fehler wenn Raum bereits voll ist
     */
    @Override
    public void enterRoom(User user, Spielraum room) throws RoomIsFullException {
        room.addPlayer(user);
        removeUser(user);
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

    /**
     * Siehe Lobby Server
     *
     * @return  aktuelle Bestenliste wird zurückgegeben
     */
    @Override
    public HashMap getBestenliste() {
        return bestenliste;
    }


}
