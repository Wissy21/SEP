package ExplodingKittens.Lobby;

import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * Interface das die Lobbyfunktionen für den Client bereitstellt
 */
public interface LobbyClient{

    /**
     * Methode die die Lobby darstellt
     *
     * @param stage Stage wird übergeben um die erzeugte Grafik sichtbar zu machen
     */
    void displayLobby(Stage stage) throws RemoteException;

    /**
     * Methode um die Bestenliste zu zeigen
     *
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    void showBestenliste() throws RemoteException;
}
