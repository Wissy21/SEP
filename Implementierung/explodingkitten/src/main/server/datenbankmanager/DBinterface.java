package main.server.datenbankmanager;

import main.exceptions.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

public interface DBinterface extends Remote {

    Connection verbindung() throws RemoteException, SQLException, ClassNotFoundException;

    boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws RemoteException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException, WrongPasswordException, NotEqualPassWordException;

    boolean spielerAnmelden(String nickname, String pass) throws RemoteException, UserNotExistException, WrongPasswordException, SQLException, ClassNotFoundException, AccountOnlineException;

    void spielerAbmelden(String nickname) throws RemoteException, SQLException, ClassNotFoundException;

    boolean kontoLoeschen(String nickname) throws RemoteException, SQLException, ClassNotFoundException;

    boolean datenAendern(String altnickname, String neunickname, String neupass, String passbest) throws RemoteException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException, NotEqualPassWordException;

    void raumErstellen(String username, String raumname) throws RemoteException, SQLException, ClassNotFoundException, RaumnameVergebenException;

    void raumBeitreten(String username, String raumname) throws RemoteException, SQLException, ClassNotFoundException, RaumNotExistException, SpielraumVollException;

    boolean raumVerlassen(String username, String raumname) throws RemoteException, SQLException, ClassNotFoundException;

    void siegEintragen(String spielername) throws RemoteException,SQLException,ClassNotFoundException;

    ArrayList<Row> getBestenliste() throws RemoteException,SQLException,ClassNotFoundException;
}
