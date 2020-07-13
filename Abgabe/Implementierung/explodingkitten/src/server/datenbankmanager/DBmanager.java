package server.datenbankmanager;

import exceptions.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;

/**
 * die Klasse verwaltet die Datenbank
 */
public class DBmanager extends UnicastRemoteObject implements DBinterface {

    /**
     * Konstruktor für den Datenbankmanager
     * @throws RemoteException Fehler bei RMI
     */
    public DBmanager() throws RemoteException {
        super();
    }

    /**
     * Diese Methode erstellt eine Verbindung mit der Datenbank
     * @return gibt ein Verbindungsobjekt zu der Datenbank zurück
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public  Connection verbindung() throws SQLException, ClassNotFoundException {

        String url = "jdbc:postgresql://localhost:5432/ExplodingKittens";
        String user = "postgres";
        String password = "postgres";

        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(url, user, password);

    }

    /**
     * Die Methode erstellt einen neuen Benutzer
     * @param nickname der gewählte Benutzername des Users
     * @param pass das gewählte Passwort des Users
     * @param bestpass das gewählte Passwort nochmal, um das Passwort zu bestätigen
     * @return gibt true zurück, wenn das Prozess gut gelaufen ist
     * @throws UserNameAlreadyExistsException die Exception wird zurückgegeben, wenn ein anderer Benutzer schon den selben Nickname hat
     * @throws NotEqualPassWordException die Exception wird zurückgegeben, wenn das Passwort und das das Passwort zu bestätigen nicht gleich sind
     * @throws SQLException die Exception wird zurückgegeben, wenn es einen Fehler im sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public boolean spielerRegistrieren(String nickname, String pass, String bestpass) throws UserNameAlreadyExistsException, NotEqualPassWordException, SQLException, ClassNotFoundException {


        Connection conn = verbindung();
        String anfrage1 = "select pass from benutzer b where benutzername = ? ";
        String anfrage2 = "insert into benutzer values(?,?,?,?) ";
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
                pstmt2.setInt(3,0);
                pstmt2.setBoolean(4,false);

                boolean check = pstmt2.execute();

                return !check;
            } else {

                throw new NotEqualPassWordException();
            }

        }

    }

    /**
     * Die Methode loggt einen Benutzer mit seinen Daten ein
     * @param nickname das Benutzername
     * @param pass das Benutzerpasswort
     * @return gibt true zurück, wenn das Prozess gut gelaufen ist.
     * @throws UserNotExistException die Exception wird zurückgegeben, wenn der gegebene Name nicht in der Datenbank existiert
     * @throws WrongPasswordException die Exception wird zurückgegeben, wenn das Passwort nicht mit dem Benutzername stimmt
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     * @throws AccountOnlineException wird geworfen wenn schon ein anderer Nutzer auf diesem Account online ist
     */
    public boolean spielerAnmelden(String nickname, String pass) throws UserNotExistException, SQLException, ClassNotFoundException, WrongPasswordException, AccountOnlineException {

        String anfrage1 = "select pass, online from benutzer b where benutzername = ? ";
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

            if (rst2.next()) {
                if (rst1.getBoolean("online")) {
                    throw new AccountOnlineException();
                } else {

                    String anfrage3 = "update benutzer set online = true where benutzername = ?";
                    PreparedStatement pstmt3 = conn.prepareStatement(anfrage3);
                    pstmt3.setString(1, nickname);
                    return pstmt3.execute();
                }
            }
            throw new WrongPasswordException();
        } else {
            throw new UserNotExistException();
        }
    }


    /**
     * Methode loggt den SPieler aus dem Account aus
     * @param name Name des Accounts
     * @throws SQLException Fehler im SQL code
     * @throws ClassNotFoundException Klasse nicht gefunden
     */
    public void spielerAbmelden(String name) throws SQLException, ClassNotFoundException {

        String anfrage1 = "update benutzer set online = false where benutzername = ?";

        Connection conn = verbindung();
        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1, name);
        pstmt1.execute();


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
     * @throws UserNameAlreadyExistsException die Exception wird zurückgegeben, wenn ein anderer Benutzer schon den selben Nickname hat
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     */
    public  boolean datenAendern(String altnickname, String neunickname, String neupass, String passbest) throws NotEqualPassWordException, UserNameAlreadyExistsException, SQLException, ClassNotFoundException {

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

    /**
     * Erstellt einen Raum in der Datenbank
     * @param username Name des erstellenden Spielers
     * @param raumname Name des Raums der erstellt werden soll
     * @throws SQLException Fehler im SQL code
     * @throws ClassNotFoundException Klasse nicht gefunden
     * @throws RaumnameVergebenException wird geworfen, wenn der Raumname bereits vergeben ist
     */
    public void raumErstellen(String username, String raumname) throws SQLException, ClassNotFoundException, RaumnameVergebenException {
        String anfrage1 = "select name from räume where name = ? ";
        String anfrage2 = "insert into räume values(?) ";
        String anfrage3 = "insert into inRaum values(?,?) ";

        Connection conn = verbindung();
        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1, raumname);
        ResultSet rst1 = pstmt1.executeQuery();
        if(rst1.next()) {
            throw new RaumnameVergebenException();
        } else {
            PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);
            pstmt2.setString(1, raumname);
            pstmt2.execute();
            PreparedStatement pstmt3 = conn.prepareStatement(anfrage3);
            pstmt3.setString(1,username);
            pstmt3.setString(2,raumname);
            pstmt3.execute();
        }
    }

    /**
     * Fügt einen Spieler in den Raum ein, der in der Datenbank gespeichert ist
     * @param username Name des Spielers
     * @param raumname Name des Raums
     * @throws SQLException Fehler im SQL code
     * @throws ClassNotFoundException Klasse nicht gefunden
     * @throws RaumNotExistException Raum existiert nicht mehr
     * @throws SpielraumVollException Raum ist bereits voll
     */
    public void raumBeitreten(String username, String raumname) throws SQLException, ClassNotFoundException, RaumNotExistException, SpielraumVollException {
        String anfrage1 = "select name from räume where name = ? ";
        String anfrage2 = "select count(*) as anz from inRaum where raum = ? ";
        String anfrage3 = "insert into inRaum values(?,?) ";

        Connection conn = verbindung();
        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1, raumname);
        ResultSet rst1 = pstmt1.executeQuery();
        if(rst1.next()) {
            PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);
            pstmt2.setString(1, raumname);
            ResultSet rst2 = pstmt2.executeQuery();
            int anz = rst2.getInt("anz");
            if(anz>=5) {
                throw new SpielraumVollException();
            } else {
                PreparedStatement pstmt3 = conn.prepareStatement(anfrage3);
                pstmt3.setString(1,username);
                pstmt3.setString(2,raumname);
                pstmt3.execute();
            }
        } else {
           throw new RaumNotExistException();
        }
    }

    /**
     * Trägt einen Spieler aus einem Raum in der Datenbank aus, wenn der Raum danach leer ist wird er geschlossen
     * @param username Name des Spielers
     * @param raumname Name des Raums
     * @return true wenn der Raum danach noch weiter existiert, false wenn er geschlossen wurde
     * @throws SQLException Fehler im SQL code
     * @throws ClassNotFoundException Klasse nicht gefunden
     */
    public boolean raumVerlassen(String username, String raumname) throws SQLException, ClassNotFoundException {

        Connection conn = verbindung();
        String anfrage = "delete from inRaum where spieler = ? and raum = ?";
        String anfrage2 = "select count(*) as anz from inRaum where raum = ? ";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        pstmt.setString(1, username);
        pstmt.setString(2,raumname);
        pstmt.execute();
        PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);
        pstmt2.setString(1, raumname);
        ResultSet rst2 = pstmt2.executeQuery();
        boolean empty = true;
        while (rst2.next()) {
            int anz = rst2.getInt("anz");
            if(anz>1) {
                empty = false;
                break;
            }
        }
        if(empty) {
            String anfrage3 = "delete from inRaum where raum = ?";
            PreparedStatement pstmt3 = conn.prepareStatement(anfrage3);
            pstmt3.setString(1, raumname);
            pstmt3.execute();
            String anfrage4 = "delete from Räume where name = ?";
            PreparedStatement pstmt4 = conn.prepareStatement(anfrage4);
            pstmt4.setString(1, raumname);
            pstmt4.execute();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Trägt einem Spieler einen Sieg in die Bestenliste ein
     * @param username Name des Spielers
     * @throws SQLException Fehler im SQL code
     * @throws ClassNotFoundException Klasse nicht gefunden
     */
    public void siegEintragen(String username) throws SQLException, ClassNotFoundException {
        Connection conn = verbindung();
        String anfrage = "update benutzer set punkte = punkte + 1 where benutzername = ? ";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        pstmt.setString(1, username);
        pstmt.execute();
    }

    /**
     * Gibt die Bestenliste aus
     * @return Liste alle Zeilen der Bestenliste
     * @throws SQLException Fehler im SQL code
     * @throws ClassNotFoundException Klasse nicht gefunden
     */
    public ArrayList<Row> getBestenliste() throws SQLException, ClassNotFoundException {
        Connection conn = verbindung();
        String anfrage = "select benutzername,punkte,dense_rank() over(order by punkte desc) as platz from benutzer order by platz ";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        ArrayList<Row> result = new ArrayList<>();
        ResultSet rslt = pstmt.executeQuery();
        while (rslt.next()) {
            result.add(new Row(rslt.getInt("platz"),rslt.getInt("punkte"),rslt.getString("benutzername")));
        }
        return result;
    }
}
