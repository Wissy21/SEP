package ExplodingKittens.Bestenliste;

import ExplodingKittens.Lobby.LobbyServer;
import ExplodingKittens.User.User;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Interface um die Bestenliste auf dem Server darzustellen
 */
public interface BestenlisteServer extends LobbyServer {


    /**
     * Methode die einem Spieler einen Sieg in der Bestenliste hinzufügt
     *
     * @param user              Benutzer der das Spiel gewonnen hat
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    void addVictory(User user) throws RemoteException;

    /**
     * Methode die die aktuelle Bestenliste liefert, um sich diese anzusehen
     *
     * @return                  Bestenliste wird zurückgegeben
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    HashMap<String,Integer> getBestenliste() throws RemoteException;

    /**
     * Methode die einen neuen Spieler in die Bestenlsite hinzufügt
     *
     * @param user              Spieler der hinzugefügt werden soll
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    void addUserListe(User user) throws RemoteException;
}
