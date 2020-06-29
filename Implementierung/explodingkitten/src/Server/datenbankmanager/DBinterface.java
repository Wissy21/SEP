package Server.datenbankmanager;

import Exceptions.UserNameAlreadyExistsException;
import Exceptions.UserNotExistException;
import Exceptions.WrongPasswordException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;

public interface DBinterface extends Remote {

    public Connection verbindung() throws RemoteException, SQLException, ClassNotFoundException;

    public boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws RemoteException, UserNameAlreadyExistsException, WrongPasswordException, SQLException, ClassNotFoundException, WrongPasswordException;

    public boolean spielerAnmelden(String nickname, String pass) throws RemoteException, UserNotExistException, WrongPasswordException, SQLException, ClassNotFoundException;

    public boolean kontoLoeschen(String nickname) throws RemoteException, SQLException, ClassNotFoundException;

    public boolean DatenAendern(String altnickname, String neunickname, String neupass, String passbest) throws RemoteException, WrongPasswordException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException, WrongPasswordException;
}
