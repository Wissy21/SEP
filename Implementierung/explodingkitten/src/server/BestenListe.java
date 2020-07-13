package server;

import java.sql.*;
import java.util.List;

public class BestenListe {
    public List<Mensch> bestenliste;

    /**
     * die Methode gibt die besten 30 Mensch zurück
     * @throws ClassNotFoundException die Exception wird zurückgegeben, wenn die Klasse nicht gefunden ist
     * @throws SQLException die Exception wird zurückgegeben, wenn es ein Fehler in sql code gibt
     */
    public void bestenlisteAnsehen() throws ClassNotFoundException, SQLException {

        String url = "jdbc:postgresql://localhost:5432/ExplodingKittens";
        String user = "postgres";
        String password = "postgres";
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        String anfrage = "select benutzername, punkt from benutzer order by  punkt desc limit 30";
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(anfrage);
        while (rset.next()){
            System.out.print(rset.getString("benutzername")+"\t");
            System.out.print(rset.getInt("punkt"));
        }
    }
}
