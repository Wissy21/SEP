package Server;

/**
 * Interface um die Bestenliste auf dem Server darzustellen
 */
public interface BestenlisteServer extends LobbyServer {


    /**
     * Methode die einem Spieler einen Sieg in der Bestenliste hinzufügt
     *
     * @param user              Benutzer der das Spiel gewonnen hat
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public void addVictory(User user) throws RemoteException;

    /**
     * Methode die die aktuelle Bestenliste liefert, um sich diese anzusehen
     *
     * @return                  Bestenliste wird zurückgegeben
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public HashMap getBestenliste() throws RemoteException;

    /**
     * Methode die einen neuen Spieler in die Bestenlsite hinzufügt
     *
     * @param user              Spieler der hinzugefügt werden soll
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public void addUserListe(User user) throws RemoteException;
}
