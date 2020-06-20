package Server;

/**
 * TODO
 */
public class Spielraum {
    String name;
    ArrayList<User> players;


    public Spielraum(User user,String name) {
        players.add(user);
        this.name = name;
    }

    public void addPlayer(User user) throws RoomIsFullException {
        if(players.size()<5) {
            players.add(user);
        } else {
            throw new RoomIsFullException();
        }
    }

    public void startGame() {
        //TODO
    }

    public void addBot() throws RoomIsFullException {
        if(players.size()<5) {
            players.add(new Bot("BOT" + players.size()));
        } else {
            throw new RoomIsFullException();
        }
    }

    public User leaveRoom(User user) {
        players.remove(user);
        return user;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
