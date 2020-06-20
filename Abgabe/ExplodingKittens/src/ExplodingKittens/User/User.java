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

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

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
