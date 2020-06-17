package ExplodingKittens.Login;

import ExplodingKittens.Exceptions.UserNotExistException;
import ExplodingKittens.Exceptions.UsernameTakenException;
import ExplodingKittens.Exceptions.WrongPasswordException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface das die Methoden für den Login-/Registrier-Teil des Servers bereitstellt
 */
public interface LoginServer extends Remote {
    /**
     * Methode die den Spieler in seinen Account einloggt, nachdem er seine Daten eingegeben hat
     *
     * @param name                      Benutzername der zum einloggen verwendet wird
     * @param password                  Zum Namen gehöriges Passwort
     * @throws RemoteException          Fehler bei Datenübertragung
     * @throws WrongPasswordException   Fehler bei Passworteingabe
     * @throws UserNotExistException    Fehler bei Dateneingabe
     */
    public void login(String name, String password) throws RemoteException, WrongPasswordException, UserNotExistException;

    /**
     * Methode die einen neuen Account für einen Spieler erstellt
     *
     * @param name                      Benutzername des neuen Accounts
     * @param password                  Passwort für den neuen Account
     * @throws RemoteException          Fehler bei Datenübertragung
     * @throws UsernameTakenException   Fehler bei Auswahl des Namens
     */
    public void register(String name, String password) throws RemoteException, UsernameTakenException;
}
