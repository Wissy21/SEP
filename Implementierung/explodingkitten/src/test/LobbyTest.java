package test;

import main.server.Lobby;
import main.server.Nachricht;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {
    Lobby lobby = new Lobby();

    public LobbyTest() throws RemoteException {
    }

    @Test
    public void chatTest() {
        ArrayList<Nachricht> chat = new ArrayList<>();
        ArrayList<Nachricht> lobbychat = lobby.getMessage();
        assertEquals(chat, lobbychat);
        Nachricht n = new Nachricht("Hallo", "Ich", "Heute");
        chat.add(n);
        lobby.sendMessage("Hallo", "Heute", "Ich");
        lobbychat = lobby.getMessage();
        for (int i = 0; i < chat.size(); i++) {
            for (int j = i; j < lobbychat.size(); j++) {
                assertEquals(chat.get(i).sender, lobbychat.get(j).sender);
                assertEquals(chat.get(i).message, lobbychat.get(j).message);
                assertEquals(chat.get(i).time, lobbychat.get(j).time);
            }
        }
    }

    @Test
    public void roomTest() {
        ArrayList<String> rooms = new ArrayList<>();
        ArrayList<String> lobbyrooms = lobby.getRooms();
        assertEquals(rooms, lobbyrooms);
        String raum = "Raumname";
        rooms.add(raum);
        lobby.addroom(raum);
        lobbyrooms = lobby.getRooms();
        assertEquals(rooms, lobbyrooms);
    }
}