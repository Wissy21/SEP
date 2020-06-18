package ExplodingKittens.User;

import ExplodingKittens.Spielraum.Spielraum;

import java.io.Serializable;

/**
 * Klasse die einen Benutzer darstellt
 */
public class User implements Serializable {
    private String name;
    private boolean isInGame;
    private Spielraum room;

    /**
     * Konsatruktor für einen neuen Benutzer
     *
     * @param name      Name des Benutzers
     */
    public User(String name) {
        this.name = name;
        isInGame = false;
    }

    /**
     *  Set Methode für den Benutzername
     *
     * @param name  Neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Methode für den Benutzername
     *
     * @return  Name wird ausgegeben
     */
    public String getName() {
        return name;
    }

    /**
     *  Set Methode für den Benutzername
     *
     * @param inGame    Status wird übergeben
     */
    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    /**
     * Get Methode ob Nutzer in einem Spiel ist
     *
     * @return  Status
     */
    public boolean isInGame() {
        return isInGame;
    }

    public void setRoom(Spielraum room) {
        this.room = room;
    }

    public Spielraum getRoom() {
        return room;
    }

    public boolean isBot() {
        return false;
    }
}
