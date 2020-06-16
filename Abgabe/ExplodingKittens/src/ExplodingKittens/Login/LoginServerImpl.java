package ExplodingKittens.Login;

import ExplodingKittens.Exceptions.UserNotExistException;
import ExplodingKittens.Exceptions.UsernameTakenException;
import ExplodingKittens.Exceptions.WrongPasswordException;
import ExplodingKittens.Lobby.Lobby;
import ExplodingKittens.User.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse die den Login Server implementiert
 */
public class LoginServerImpl extends UnicastRemoteObject implements LoginServer{

    /**
     * HashMap in der die Nutzerdaten gespeichert werden
     */
    private HashMap<String,String> users = new HashMap<>();
    private Lobby lobby;

    /**
     * Konstruktor
     * @throws RemoteException
     */
    public LoginServerImpl() throws RemoteException {}

    /**
     * Konstruktor für den Server
     *
     * @param lobby             Nach dem Login / der Anmeldung sollen die Daten an die Lobby weitergegeben werden
     *                          und der Nutzer dorthin transferiert werden
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public LoginServerImpl(Lobby lobby) throws RemoteException {
        this.lobby = lobby;
    }

    /**
     * Methode die den Server startet und ihn in der Registry hinterlegt
     *
     * @param args
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        LoginServerImpl server = new LoginServerImpl();
        reg.rebind("login-server",server);
    }

    /**
     * Im interface beschrieben
     * HashMap wird nach Benutzername und Passwort Kombination durchsucht
     * Bei erfolgreichem anmelden werden die Daten an die Lobby weitergegeben und der Nutzer ist angemeldet
     */
    @Override
    public void login(String name, String password) throws WrongPasswordException, UserNotExistException {
        for (Map.Entry<String,String> e: users.entrySet()) {
            if(name.equals(e.getKey())){
                if(password.equals(e.getValue())) {
                    lobby.addUser(new User(name));
                    return;
                }
                throw new WrongPasswordException();
            }
        }
        throw new UserNotExistException();
    }

    /**
     * Im interface beschrieben
     * HashMap wird durchsucht, damit sich der Beutzername nicht doppelt
     * Wenn dem nicht der Fall ist werden die Daten in die HashMap aufgenommen und der Nutzer in die Lobby weitergeleitet
     */
    @Override
    public void register(String name, String password) throws UsernameTakenException {
        for (String s: users.keySet()) {
            if(name.equals(s)){
                throw new UsernameTakenException();
            }
        }
        users.put(name, password);
        lobby.addUser(new User(name));
    }
}
