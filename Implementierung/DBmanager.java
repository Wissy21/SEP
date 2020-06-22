package main.server.datenbankmanager;

import main.exceptions.UserNameAlreadyExistsException;
import main.exceptions.UserNotExistException;
import main.exceptions.WrongPassWordException;
import main.server.Benutzer;

import java.sql.*;
import java.util.List;

/**
 *
 */
public class DBmanager {

    public List<Benutzer> benutzerList;

    public static Connection verbindung() throws SQLException, ClassNotFoundException {

        String url ="jdbc:postgresql://localhost:5432/ExplodingKittens" ;
        String user = "postgres";
        String password = "postgres";

        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection (url, user, password);

        return conn;

    }

    public DBmanager() {
    }


    /**
     * Diese Methode hilft dem Spieler sich anzumelden
     *
     * @param nickname      Benutzername des Spielers
     * @param pass
     * @para
     * @return
     * @throws UserNotExistException
     * @throws WrongPassWordException
     */
    public static boolean spielerRegistrieren (String nickname, String pass, String bestpass) throws UserNameAlreadyExistsException, WrongPassWordException, SQLException, ClassNotFoundException {

        if (pass == bestpass) {

            Connection conn = verbindung();
            String anfrage = "insert into benutzer values(?,?) ";
            PreparedStatement pstmt = conn.prepareStatement(anfrage);
            pstmt.setString(1, nickname);
            pstmt.setString(2, pass);

            boolean check = pstmt.execute();

            return !check;
        }

        else {

            return false;
        }

    }

    public static boolean  spielerAnmelden(String nickname, String pass) throws UserNotExistException, WrongPassWordException, SQLException, ClassNotFoundException {

        String anfrage1 = "select pass from benutzer b where benutzername = ? ";
        String anfrage2 = "select pass from benutzer b where benutzername = ? and pass = ? ";
        Connection conn = verbindung();

        PreparedStatement pstmt1 = conn.prepareStatement(anfrage1);
        pstmt1.setString(1,nickname);
        ResultSet rst1 = pstmt1.executeQuery();

        if(rst1.next()) {

        PreparedStatement pstmt2 = conn.prepareStatement(anfrage2);
        pstmt2.setString(1, nickname);
        pstmt2.setString(2, pass);
        ResultSet rst2 =  pstmt2.executeQuery();

        while(rst2.next()){

                return true;

        }

        return false;}

        else
            return false;

    }

    /**
     *
     * @param nickname
     * @return
     * @throws UserNotExistException
     */
    public static boolean kontoLoeschen(String nickname) throws  SQLException, ClassNotFoundException {


        Connection conn = verbindung();
        String anfrage = "delete from benutzer where benutzername = ?";
        PreparedStatement pstmt = conn.prepareStatement(anfrage);
        pstmt.setString(1, nickname);

        boolean check = pstmt.execute();

        return !check;
    }

    public static boolean DatenAendern(String altnickname, String neunickname, String neupass, String passbest ) throws WrongPassWordException, UserNotExistException, SQLException, ClassNotFoundException {

        if(neupass==passbest){

            Connection conn = verbindung();
            String anfrage = "update benutzer set benutzername = ?, pass = ? where benutzername = ?";
            PreparedStatement pstmt = conn.prepareStatement(anfrage);
            pstmt.setString(1, neunickname);
            pstmt.setString(2, neupass);
            pstmt.setString(3, altnickname);

            boolean check = pstmt.execute();

            return !check;
        }
        else{

            return false;
        }

    }
}
