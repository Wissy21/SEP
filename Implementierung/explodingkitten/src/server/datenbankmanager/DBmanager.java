package server.datenbankmanager;

import exceptions.NotEqualPassWordException;
import exceptions.UserNameAlreadyExistsException;
import exceptions.UserNotExistException;
import exceptions.WrongPasswordException;
import server.Benutzer;

import java.sql.*;
import java.util.List;

/**
 * die Klasse verwaltet die Datenbank
 */
public class DBmanager {

    public List<Benutzer> benutzerList;

    /**
     * Diese Methode erstellt eine Verbindung mit der Datenbank
     * @return gibt ein Verbingsobjekt zu der Datenbank zurück
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public static Connection verbindung() throws SQLException, ClassNotFoundException {

        String url = "jdbc:postgresql://localhost:5432/ExplodingKittens";
        String user = "postgres";
        String password = "postgres";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;

    }

    /**
     * Die Methode erstellt einen neuen Benutzer
     * @param nickname der gewählte Benutzername des Users
     * @param pass das gewählte Passwort des Users
     * @param bestpass das gewählte Passwort nochmal, um das Passwort zu bestätigen
     * @return gibt true zurück, wenn das Prozess gut gelaufen ist
     * @throws UserNameAlreadyExistsException die Exception wird zurückgegeben, wenn ein anderer Benutzer schon derselbe Nickname hat
     * @throws NotEqualPassWordException die Exception wird zurückgegeben, wenn das Passwort und das das Passwort zu bestätigen nicht gleich sind
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws UserNameAlreadyExistsException, NotEqualPassWordException, SQLException, ClassNotFoundException {


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

                throw new NotEqualPassWordException();
            }

        }

    }

    /**
     * Die Methode loggt einen Benutzer mit seineen Daten ein
     * @param nickname das Betnutzername
     * @param pass das Benutzerpasswort
     * @return gibt true zurück, wenn das Prozess gut gelaufen ist.
     * @throws UserNotExistException die Exception wird zurückgegeben, wenn der gegebene Name nicht in der Datenbank existiert
     * @throws WrongPasswordException die Exception wird zurückgegeben, wenn das Passwort nicht mit dem Benutzername stimmt
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */

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
     * Die Methode löscht das Konto von dem eingeloggten Benutzer
     * @param nickname der Benutzername des Users
     * @return gibt true wenn das Prozess gut gelaufen ist
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public boolean kontoLoeschen(String nickname) throws SQLException, ClassNotFoundException {

        Connection conn = verbindung();
        String anfrage = "delete from benutzer where benutzername = ?";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        pstmt.setString(1, nickname);

        boolean check = pstmt.execute();
        return !check;
    }

    /**
     * Die Methode ändern die Daten von dem eingeloggten Benutzer
     * @param altnickname der alte Benutzername
     * @param neunickname der neue Benutzername
     * @param neupass das neue Passwort
     * @param passbest das neue Passwort nochmal zum bestätigen
     * @return gibt true zurück, wenn das Prozess gut gelaufen ist.
     * @throws NotEqualPassWordException die Exception wird zurückgegeben, wenn das Passwort und das das Passwort zu bestätigen nicht gleich sind
     * @throws UserNameAlreadyExistsException die Exception wird zurückgegeben, wenn ein anderer Benutzer schon derselbe Nickname hat
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public static boolean datenAendern(String altnickname, String neunickname, String neupass, String passbest) throws NotEqualPassWordException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException {

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
            }

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
                } else {
                    throw new NotEqualPassWordException();
                }
            }
        }
    }
}
