package server.datenbankmanager;

import exceptions.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;

public interface DBinterface extends Remote {

    public  Connection verbindung() throws RemoteException, SQLException, ClassNotFoundException;

    public boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws RemoteException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException, WrongPasswordException, NotEqualPassWordException;

    public boolean spielerAnmelden(String nickname, String pass) throws RemoteException, UserNotExistException, WrongPasswordException, SQLException, ClassNotFoundException;

    public boolean kontoLoeschen(String nickname) throws RemoteException, SQLException, ClassNotFoundException;

    public boolean datenAendern(String altnickname, String neunickname, String neupass, String passbest) throws RemoteException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException, NotEqualPassWordException;

    public void raumErstellen(String username, String raumname) throws RemoteException, SQLException, ClassNotFoundException, RaumnameVergebenException;

    public void raumBeitreten(String username, String raumname) throws RemoteException, SQLException, ClassNotFoundException, RaumNotExistException, SpielraumVollException;

    public boolean raumVerlassen(String username, String raumname) throws RemoteException, SQLException, ClassNotFoundException;
}
