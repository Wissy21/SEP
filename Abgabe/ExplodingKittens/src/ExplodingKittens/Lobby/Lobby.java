package ExplodingKittens.Lobby;

import ExplodingKittens.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse die die Lobby implementiert
 * von hier aus können Spielräume geöffnet und beigetreten werden,
 * Die Bestenliste angeschaut werden
 * Sich abgemeldet werden
 * Und man kann sich mit anderen Spielern über einen Chat unterhalten
 */
public class Lobby {
    List<User> users = new ArrayList<>();

    /**
     * Methode die einen Benutzer in dei Liste der Spieler hinzufügt, die gerade in der Lobby sind
     *
     * @param user Benutzer der hinnzugefügt werden soll
     */
    public void addUser(User user) {
        users.add(user);
    }
}
