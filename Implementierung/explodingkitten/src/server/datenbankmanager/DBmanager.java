package server.datenbankmanager;

import exceptions.*;
import exceptions.UserNameAlreadyExistsException;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

/**
 *
 */
public class DBmanager extends UnicastRemoteObject implements DBinterface {


    public Connection verbindung() throws SQLException, ClassNotFoundException {

        String url = "jdbc:postgresql://localhost:5432/ExplodingKittens";
        String user = "postgres";
        String password = "123";

        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;

    }

    public DBmanager() throws RemoteException {
        super();
    }


    /**
     * Diese Methode hilft dem Spieler sich anzumelden
     *
     * @param nickname Benutzername des Spielers
     * @param pass
     * @return
     * @throws UserNotExistException
     * @throws WrongPasswordException
     * @para
     */
    public boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws UserNameAlreadyExistsException, SQLException, ClassNotFoundException, WrongPasswordException {
        Connection conn = verbindung();
        String anfrage1 = "select pass from benutzer b where benutzername = ? ";
        String anfrage2 = "insert into benutzer values(?,?) ";
        PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);
        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1, nickname);
        ResultSet rst1 = pstmt1.executeQuery();

        if (rst1.next()) {
            throw new UserNameAlreadyExistsException();
        } else {
            if (pass.equals(bestpass)) {
                pstmt2.setString(1, nickname);
                pstmt2.setString(2, pass);

                boolean check = pstmt2.execute();
                return !check;
            } else {
                throw new WrongPasswordException();
            }
        }
    }

    public boolean spielerAnmelden(String nickname, String pass) throws UserNotExistException, WrongPasswordException, SQLException, ClassNotFoundException {

        String anfrage1 = "select pass from benutzer b where benutzername = ? ";
        String anfrage2 = "select pass from benutzer b where benutzername = ? and pass = ? ";
        Connection conn = verbindung();

        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1, nickname);
        ResultSet rst1 = pstmt1.executeQuery();

        if (rst1.next()) {
            PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);
            pstmt2.setString(1, nickname);
            pstmt2.setString(2, pass);
            ResultSet rst2 = pstmt2.executeQuery();

            while (rst2.next()) {
                return true;
            }
            throw new WrongPasswordException();
        } else
            throw new UserNotExistException();
    }

    /**
     * @param nickname
     * @return
     * @throws UserNotExistException
     */
    public boolean kontoLoeschen(String nickname) throws SQLException, ClassNotFoundException {
        Connection conn = verbindung();
        String anfrage = "delete from benutzer where benutzername = ?";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        pstmt.setString(1, nickname);

        boolean check = pstmt.execute();
        return !check;
    }

    public boolean datenAendern(String altnickname, String neunickname, String neupass, String passbest) throws UserNameAlreadyExistsException, SQLException, ClassNotFoundException, NotEqualPassWordException {
        Connection conn = verbindung();
        String anfrage1 = "select pass from benutzer b where benutzername = ? ";
        String anfrage = "update benutzer set benutzername = ?, pass = ? where benutzername = ?";
        String anfrage2 = "update benutzer set pass = ? where benutzername = ?";

        PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);

        if (neunickname.equals(altnickname)) {

            if (neupass.equals(passbest)) {

                pstmt2.setString(1, neupass);
                pstmt2.setString(2, neunickname);
                boolean check = pstmt2.execute();
                return !check;
            } else
                throw new NotEqualPassWordException();

        } else {
            PreparedStatement pstmt = conn.prepareStatement(anfrage);
            pstmt.setString(1, neunickname);
            PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
            pstmt1.setString(1, neunickname);
            ResultSet rst1 = pstmt1.executeQuery();

            if (rst1.next()) {
                throw new UserNameAlreadyExistsException();
            } else {
                if (neupass.equals(passbest)) {
                    pstmt.setString(2, neupass);
                    pstmt.setString(3, altnickname);
                    boolean check = pstmt.execute();
                    return !check;
                } else
                    throw new NotEqualPassWordException();
            }
        }
    }
}