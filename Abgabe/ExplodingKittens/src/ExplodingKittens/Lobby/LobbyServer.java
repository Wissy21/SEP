package ExplodingKittens.Lobby;

import ExplodingKittens.Exceptions.RoomIsFullException;
import ExplodingKittens.Login.LoginServer;
import ExplodingKittens.Spielraum.Spielraum;
import ExplodingKittens.User.User;

import java.rmi.RemoteException;

/**
 * Inteface das Methoden der Lobby bereitstellt
 * Von hier aus kann :  Spielräume geöffnet und beigetreten werden,
 *                      Die Bestenliste angeschaut werden
 *                      Sich abgemeldet werden
 *                      Man sich mit anderen Spielern über einen Chat unterhalten
 */
public interface LobbyServer extends LoginServer {


    /**
     * Methode die einen Benutzer in die Liste der Spieler hinzufügt, die gerade in der Lobby sind
     *
     * @param user Benutzer der hinzugefügt werden soll
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public void addUserLobby(User user) throws RemoteException;

    /**
     * Methode die einen Benutzer aus der Liste der Spieler entfernt, die gerade in der Lobby sind
     *
     * @param user              Benutzer der entfernt werden soll
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public void removeUser(User user) throws RemoteException;

    /**
     * Methode die einen neuen Spielraum erstellt
     *
     * @param user                  Benutzer der den Raum erstellen will
     * @param name                  Name für den Raum
     * @throws RemoteException      Fehler bei Datenübertragung
     * @throws RoomIsFullException  Fehler wenn Raum bereits voll ist
     */
    public void createRoom(User user, String name) throws RemoteException, RoomIsFullException;

    /**
     * Methode um einem Raum beizutreten
     *
     * @param user                  Benutzer der dem Raum beitreten will
     * @param room                  Raum den beigetreten werden soll
     * @throws RemoteException      Fehler bei Datenübertragung
     * @throws RoomIsFullException  Fehler wenn Raum voll ist
     */
    public void enterRoom(User user, Spielraum room) throws RemoteException, RoomIsFullException;
}
