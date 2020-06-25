package ExplodingKittens.Lobby;

import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Interface das die Lobbyfunktionen für den Client bereitstellt
 */
public interface LobbyClient{

    /**
     * Methode die die Lobby darstellt
     *
     * @param stage Stage wird übergeben um die erzeugte Grafik sichtbar zu machen
     * @throws IOException  Fehler bei IO
     */
    void displayLobby(Stage stage) throws IOException;

    /**
     * Methode um die Bestenliste zu zeigen
     *
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    void showBestenliste() throws RemoteException;
}
