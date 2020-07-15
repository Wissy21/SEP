package server;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LobbyTest {
    Lobby lobby = new Lobby();

    public LobbyTest() throws RemoteException {
    }
    @Test
    public void chatTest() {
        ArrayList<Nachricht> chat = new ArrayList<>();
        ArrayList<Nachricht> lobbychat = lobby.getMessage();
        assertEquals(chat,lobbychat);
        Nachricht n = new Nachricht("Hallo","Ich","Heute");
        chat.add(n);
        lobby.sendMessage("Hallo","Heute","Ich");
        lobbychat = lobby.getMessage();
        for(int i= 0;i<chat.size();i++) {
            for(int j = i;j<lobbychat.size();j++) {
                assertEquals(chat.get(i),lobbychat.get(j));
            }
        }
    }

    @Test
    public void roomTest () {
        ArrayList<String> rooms = new ArrayList<>();
        ArrayList<String> lobbyrooms = lobby.getRooms();
        assertEquals(rooms,lobbyrooms);
        String  raum = "Raumname";
        rooms.add(raum);
        lobby.addroom(raum);
        lobbyrooms = lobby.getRooms();
        assertEquals(rooms,lobbyrooms);
    }
}
