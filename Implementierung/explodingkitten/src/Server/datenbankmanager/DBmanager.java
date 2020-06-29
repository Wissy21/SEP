package Server.datenbankmanager;

import Exceptions.*;
import Server.Benutzer;
import Exceptions.UserNameAlreadyExistsException;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;

/**
 *
 */
public class DBmanager extends UnicastRemoteObject implements DBinterface {

    public List<Benutzer> benutzerList;

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
    public boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws UserNameAlreadyExistsException, WrongPasswordException, SQLException, ClassNotFoundException, WrongPasswordException {


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

            System.out.println(pass);
            System.out.println(bestpass);
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

    public boolean DatenAendern(String altnickname, String neunickname, String neupass, String passbest) throws WrongPasswordException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException, WrongPasswordException {


        Connection conn = verbindung();
        String anfrage1 = "select pass from benutzer b where benutzername = ? ";
        String anfrage = "update benutzer set benutzername = ?, pass = ? where benutzername = ?";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        pstmt.setString(1, neunickname);
        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1, neunickname);
        ResultSet rst1 = pstmt1.executeQuery();

        if (rst1.next()) {

            throw new UserNameAlreadyExistsException();

        } else {

            if (neupass == passbest) {


                pstmt.setString(2, neupass);
                pstmt.setString(3, altnickname);

                boolean check = pstmt.execute();

                return !check;
            } else {

                throw new WrongPasswordException();

            }
        }

    }
}
