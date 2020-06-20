package Client;

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
    public void displayLobby(Stage stage);

    /**
     * Methode um die Bestenliste zu zeigen
     *
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    public void showBestenliste() throws RemoteException;

    /**
     * TODO
     *
     * @param stage
     */
    public void leaveLobbyToRoom(Stage stage);
}
