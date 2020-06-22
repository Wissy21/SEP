package Tests;

import ExplodingKittens.Exceptions.*;
import ExplodingKittens.Server.ServerImpl;
import ExplodingKittens.Spielraum.Spielraum;
import ExplodingKittens.User.User;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerImplTest {

    @Test
    void loginExistingUser() throws RemoteException {
        ServerImpl server = new ServerImpl();
        assertDoesNotThrow(()->{server.register("Test","Test123");});
        assertDoesNotThrow(() -> {server.login("Test","Test123");});
    }

    @Test
    void loginUnknownUser() throws RemoteException {
        ServerImpl server = new ServerImpl();
        assertThrows(UserNotExistException.class, () -> {server.login("Test","Fehler");});

    }

    @Test
    void loginWrongPassword() throws RemoteException {
        ServerImpl server = new ServerImpl();
        assertDoesNotThrow(()->{server.register("Test","Test123");});
        assertThrows(WrongPasswordException.class, () -> {server.login("Test","Fehler");});

    }

    @Test
    void registerNewUser() throws RemoteException {
        ServerImpl server = new ServerImpl();
        assertDoesNotThrow(()->{server.register("Test","Test123");});
    }

    @Test
    void registerExistingUser() throws RemoteException {
        ServerImpl server = new ServerImpl();
        assertDoesNotThrow(()->{server.register("Test","Fehler");});
        assertThrows(UsernameTakenException.class,()->{server.register("Test","Fehler");});
    }


    @Test
    void createRoomNew() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser = new User("Test");
        Spielraum testRoom = new Spielraum(testUser,"Test");
        assertDoesNotThrow(() -> {Spielraum serverRoom = server.createRoom(testUser,"Test");
                                  assertEquals(testRoom,serverRoom);});
    }

    @Test
    void createRoomExisting() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        assertDoesNotThrow(() -> {server.createRoom(testUser1,"Test");});
        assertThrows(RoomNameTakenException.class, () -> {server.createRoom(testUser2,"Test");});
        }

    @Test
    void createRoomNoInput() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser = new User("Test");
        assertThrows(NoInputException.class, () -> {server.createRoom(testUser,null);});
    }

    @Test
    void enterRoomFreeSpace() throws RemoteException {
            ServerImpl server = new ServerImpl();
            User testUser1 = new User("Test1");
            User testUser2 = new User("Test2");
            Spielraum testRoom = new Spielraum(testUser1,"Test");

            assertDoesNotThrow(() -> {server.createRoom(testUser1,"Test");});
            assertDoesNotThrow(() -> {server.enterRoom(testUser2,testRoom);});
        }

    @Test
    void enterRoomNoFreeSpace() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        User testUser3 = new User("Test3");
        User testUser4 = new User("Test4");
        User testUser5 = new User("Test5");
        User testUser6 = new User("Test6");
        Spielraum testRoom = new Spielraum(testUser1, "Test");

        assertDoesNotThrow(() -> {server.createRoom(testUser1,"Test");});
        assertDoesNotThrow(() -> {server.enterRoom(testUser2,testRoom);});
        assertDoesNotThrow(() -> {server.enterRoom(testUser3,testRoom);});
        assertDoesNotThrow(() -> {server.enterRoom(testUser4,testRoom);});
        assertDoesNotThrow(() -> {server.enterRoom(testUser5,testRoom);});
        assertThrows(RoomIsFullException.class, () -> {server.enterRoom(testUser6,testRoom);});

    }

    @Test
    void addVictory() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Integer> testListe = new HashMap<>();
        testListe.put("Test1",0);
        testListe.put("Test2",1);

        assertDoesNotThrow(()->{server.register("Test1","Test123");});
        assertDoesNotThrow(()->{server.register("Test2","Test123");});
        server.addVictory(testUser2);
        HashMap<String,Integer> serverListe = server.getBestenliste();
        assertEquals(testListe,serverListe);


    }

    @Test
    void addUserListe()  throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Integer> testListe = new HashMap<>();
        testListe.put("Test1",0);
        testListe.put("Test2",0);

        server.addUserListe(testUser1);
        server.addUserListe(testUser2);
        assertEquals(testListe,server.getBestenliste());
    }

    @Test
    void addUserListeOverride()  throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Integer> testListe = new HashMap<>();
        testListe.put("Test1",0);
        testListe.put("Test2",1);

        server.addUserListe(testUser1);
        server.addUserListe(testUser2);
        server.addVictory(testUser2);
        server.addUserListe(testUser2);
        assertEquals(testListe,server.getBestenliste());
    }

    @Test
    void getBestenliste() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Integer> testListe = new HashMap<>();
        testListe.put("Test1",0);
        testListe.put("Test2",0);

        assertDoesNotThrow(()->{server.register("Test1","Test123");});
        assertDoesNotThrow(()->{server.register("Test2","Test123");});
        assertEquals(testListe,server.getBestenliste());
    }

    @Test
    void getRooms() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Spielraum> testListe = new HashMap<>();
        Spielraum testRoom1 = new Spielraum(testUser1, "Test1");
        Spielraum testRoom2 = new Spielraum(testUser2, "Test2");
        testListe.put("Test1",testRoom1);
        testListe.put("Test2",testRoom2);

        assertDoesNotThrow(()-> {server.createRoom(testUser1,"Test1");});
        assertDoesNotThrow(()-> {server.createRoom(testUser2,"Test2");});
        assertEquals(testListe,server.getRooms());
    }

    @Test
    void deleteRoom() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Spielraum> testListe = new HashMap<>();
        Spielraum testRoom1 = new Spielraum(testUser1, "Test1");
        Spielraum testRoom2 = new Spielraum(testUser2, "Test2");
        testListe.put("Test1",testRoom1);

        assertDoesNotThrow(()-> {server.createRoom(testUser1,"Test1");});
        assertDoesNotThrow(()-> {server.createRoom(testUser2,"Test2");});
        server.deleteRoom(testRoom2);
        assertEquals(testListe,server.getRooms());
    }

    @Test
    void updateRoomNewName() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Spielraum> testListe = new HashMap<>();
        Spielraum testRoom1 = new Spielraum(testUser1, "Test1");
        Spielraum testRoom2 = new Spielraum(testUser2, "Test2");
        Spielraum testRoomUpdate = new Spielraum(testUser2, "TestUpdate");

        testListe.put("Test1",testRoom1);
        testListe.put("TestUpdate",testRoomUpdate);

        assertDoesNotThrow(()-> {server.createRoom(testUser1,"Test1");});
        assertDoesNotThrow(()-> {server.createRoom(testUser2,"Test2");});
        assertDoesNotThrow(()-> {server.updateRoom("Test2",testRoomUpdate);});
        assertEquals(testListe,server.getRooms());
    }

    @Test
    void updateRoomTakenName() throws RemoteException {
        ServerImpl server = new ServerImpl();
        User testUser1 = new User("Test1");
        User testUser2 = new User("Test2");
        HashMap<String,Spielraum> testListe = new HashMap<>();
        Spielraum testRoom1 = new Spielraum(testUser1, "Test1");
        Spielraum testRoom2 = new Spielraum(testUser2, "Test2");
        Spielraum testRoomUpdate = new Spielraum(testUser2, "Test1");

        testListe.put("Test1",testRoom1);
        testListe.put("TestUpdate",testRoomUpdate);

        assertDoesNotThrow(()-> {server.createRoom(testUser1,"Test1");});
        assertDoesNotThrow(()-> {server.createRoom(testUser2,"Test2");});
        assertThrows(RoomNameTakenException.class, ()-> {server.updateRoom("Test2",testRoomUpdate);});
    }
}