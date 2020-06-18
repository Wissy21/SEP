package ExplodingKittens.Spielraum;

import ExplodingKittens.Exceptions.NoPermissionException;
import ExplodingKittens.Exceptions.RoomIsFullException;
import ExplodingKittens.User.Bot;
import ExplodingKittens.User.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */

public class Spielraum implements Serializable {
    String name;
    HashMap<String,User> players = new HashMap<>();
    User owner;


    public Spielraum(User user,String name) {
        players.put(user.getName(),user);
        this.name = name;
        owner = user;
    }

    public void addPlayer(User user) throws RoomIsFullException {
        if(players.size()<5) {
            players.put(user.getName(),user);
        } else {
            throw new RoomIsFullException();
        }
    }

    public void startGame(User user) throws NoPermissionException{
        if(user.equals(getOwner())) {
            //TODO
        } else {
            throw new NoPermissionException();
        }
    }

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

    public String getName() {
        return name;
    }

    public void changeName(User user, String name) throws NoPermissionException {
        if(user.getName().equals(getOwner().getName())) {
            this.name = name;

        } else {
            throw new NoPermissionException();
        }
    }
    public User getOwner() {
        return owner;
    }
}
