package main.server.datenbankmanager;

import main.exceptions.UserNameAlreadyExistsException;
import main.exceptions.UserNotExistException;
import main.exceptions.WrongPassWordException;
import main.server.Benutzer;
import main.server.Spieler;

import java.util.List;

public class DBmanager {

    public List<Benutzer> benutzerList;

    public DBmanager() {
    }

    public boolean spielerRegistrieren(String name, String pass) throws UserNameAlreadyExistsException {
        return true;
    }

    public boolean spielerAnmelden(String name, String pass, Spieler spieler) throws UserNotExistException, WrongPassWordException {
        return true;
    }

    public boolean kontoLoeschen(String name) throws UserNotExistException {
        return true;
    }

    public boolean benutzerAendern(String name) throws UserNotExistException {
        return true;
    }
}
