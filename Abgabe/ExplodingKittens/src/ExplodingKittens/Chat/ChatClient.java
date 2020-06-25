package ExplodingKittens.Chat;

import ExplodingKittens.User.User;
import javafx.scene.control.Label;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Inteface für die Methoden zur Darstellung des Chats
 */
public interface ChatClient {
    /**
     *  Methode um eine eingegebene nachricht anzuzeigen
     *
     * @param user                  Benutzer der die Nachricht geschrieben hat
     * @param message               Nachricht die im Chat angezeigt werden soll
     * @return                      Gibt ein Label zurück das die Nachricht enthält
     * @throws RemoteException      Fehler bei Datenübertragung
     * @throws IOException          Fehler bei IO
     */
    Label displayMessage(User user, String message) throws RemoteException, IOException;
}
