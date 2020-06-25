package Tests;

import ExplodingKittens.Exceptions.NoPermissionException;
import ExplodingKittens.Exceptions.RoomIsFullException;
import ExplodingKittens.Spielraum.Spielraum;
import ExplodingKittens.User.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpielraumTest {

    @Test
    void addPlayerHasPlace() {
        User testuser1 = new User("Test1");
        User testuser2 = new User("Test2");

        Spielraum test = new Spielraum(testuser1,"Test");
        assertDoesNotThrow(()->{test.addPlayer(testuser2);});
    }

    @Test
    void addPlayerIsFull() {
        User testuser1 = new User("Test1");
        User testuser2 = new User("Test2");
        User testuser3 = new User("Test3");
        User testuser4 = new User("Test4");
        User testuser5 = new User("Test5");
        User testuser6 = new User("Test6");


        Spielraum test = new Spielraum(testuser1,"Test");
        assertDoesNotThrow(()->{test.addPlayer(testuser2);});
        assertDoesNotThrow(()->{test.addPlayer(testuser3);});
        assertDoesNotThrow(()->{test.addPlayer(testuser4);});
        assertDoesNotThrow(()->{test.addPlayer(testuser5);});
        assertThrows(RoomIsFullException.class, ()->{test.addPlayer(testuser6);});

    }
    @Test
    void addBot() {
        User testuser1 = new User("Test1");
        User testuser2 = new User("Test2");


        Spielraum test = new Spielraum(testuser1,"Test");
        assertDoesNotThrow(()->{test.addBot(testuser1);});
        assertDoesNotThrow(()->{test.addBot(testuser1);});
        assertThrows(NoPermissionException.class, ()->{test.addBot(testuser2);});
        assertDoesNotThrow(()->{test.addBot(testuser1);});
        assertDoesNotThrow(()->{test.addBot(testuser1);});
        assertThrows(RoomIsFullException.class, ()->{test.addBot(testuser1);});
    }

    @Test
    void leaveRoom() {

        User testuser1 = new User("Test1");
        User testuser2 = new User("Test2");
        User testuser3 = new User("Test3");
        User testuser4 = new User("Test4");
        User testuser5 = new User("Test5");


        Spielraum test = new Spielraum(testuser1,"Test");
        assertDoesNotThrow(()->{test.addPlayer(testuser2);});
        assertDoesNotThrow(()->{test.addPlayer(testuser3);});
        assertDoesNotThrow(()->{test.addPlayer(testuser4);});
        assertDoesNotThrow(()->{test.addPlayer(testuser5);});
        assertDoesNotThrow(()-> {boolean isEmpty = test.leaveRoom(testuser5);
                                 assertEquals(false,isEmpty);});
        assertDoesNotThrow(()-> {test.leaveRoom(testuser4);});
        assertDoesNotThrow(()-> {test.leaveRoom(testuser3);});
        assertDoesNotThrow(()-> {test.leaveRoom(testuser2);});
        assertDoesNotThrow(()-> {boolean isEmpty = test.leaveRoom(testuser1);
                                 assertEquals(true,isEmpty);});
    }

    @Test
    void changeName() {
        User testuser1 = new User("Test1");
        User testuser2 = new User("Test2");


        Spielraum test = new Spielraum(testuser1,"Test");
        assertDoesNotThrow(()->{test.changeName(testuser1,"TestUpdate");});
        assertThrows(NoPermissionException.class, ()->{test.changeName(testuser2,"TestUpdate");});
        assertDoesNotThrow(()-> {test.leaveRoom(testuser1);});
        assertDoesNotThrow(()->{test.changeName(testuser2,"TestUpdate2");});

    }

    @Test
    void getName() {
    }

    @Test
    void getOwner() {
    }
}