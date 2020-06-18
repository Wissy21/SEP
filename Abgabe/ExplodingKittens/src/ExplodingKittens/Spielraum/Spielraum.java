package ExplodingKittens.Spielraum;

import ExplodingKittens.Exceptions.NoPermissionException;
import ExplodingKittens.Exceptions.RoomIsFullException;
import ExplodingKittens.User.Bot;
import ExplodingKittens.User.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse die eine Spielraum repräsentiert
 */

public class Spielraum implements Serializable {
    /**
     * name     Name des Raums
     * palyers  Spieler im Raum
     * owner    Besitzer des Raums
     */
    String name;
    HashMap<String,User> players = new HashMap<>();
    User owner;

    /**
     * Konstruktor der einen neuen Spielraum erstellt
     *
     * @param user  Ersteller des Spielraums
     * @param name  Name des Spielraums
     */
    public Spielraum(User user,String name) {
        players.put(user.getName(),user);
        this.name = name;
        owner = user;
    }

    /**
     * Methode zum hinzufügen eines neuen Spielers
     *
     * @param user                  Spieler der hinzugefügt werden soll
     * @throws RoomIsFullException  Fehler wenn Raum bereits voll
     */
    public void addPlayer(User user) throws RoomIsFullException {
        if(players.size()<5) {
            players.put(user.getName(),user);
        } else {
            throw new RoomIsFullException();
        }
    }

    /**
     * TODO
     * @param user                      Benutzer der das Spiel starten will
     * @throws NoPermissionException    Fehler keine Berechtigung
     */
    public void startGame(User user) throws NoPermissionException{
        if(user.equals(getOwner())) {
            //TODO
        } else {
            throw new NoPermissionException();
        }
    }

    /**
     * Methode um eine Bot in den Raum hinzuzufügen
     *
     * @param user                      Benutzer der den Bot hinzufügen will
     * @throws RoomIsFullException      Fehler da Raum voll ist
     * @throws NoPermissionException    Fehler da keine Berechtigung
     */
    public void addBot(User user) throws RoomIsFullException, NoPermissionException {
        if(user.getName().equals(getOwner().getName())) {
            if (players.size() < 5) {
                players.put("BOT"+players.size(),new Bot("BOT" + players.size()));
            } else {
                throw new RoomIsFullException();
            }
        } else {
            throw new NoPermissionException();
        }
    }

    /**
     * Methode um den Raum zu verlassen
     *
     * @param user  Benutzer der den Raum verlassen will
     * @return      Varibale ob der Raum leer ist
     */
    public boolean leaveRoom(User user) {
        players.remove(user.getName());
        user.setInGame(false);
        boolean empty = true;
        User possibleNewOwner = null;
        for (Map.Entry<String,User> e : players.entrySet()) {
            if(!e.getValue().isBot()) {
                empty = false;
                possibleNewOwner = e.getValue();
            }
        }
        if(user.getName().equals(owner.getName())) {
            owner = possibleNewOwner;
        }
        System.out.println(empty);
        return empty;
    }

    /**
     * Methode um den Namen des Raums zu ändern
     *
     * @param user                      Benutzer der den Namen ändern will
     * @param name                      Neuer Name für den Raum
     * @throws NoPermissionException    Fehler keine Berechtigung
     */
    public void changeName(User user, String name) throws NoPermissionException {
        if(user.getName().equals(getOwner().getName())) {
            this.name = name;

        } else {
            throw new NoPermissionException();
        }
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }
}
