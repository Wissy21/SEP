package ExplodingKittens.Spielraum;

import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * Interface das die Methoden zur Darstellung des Spielraums liefert
 */
public interface SpielraumClient {

    /**
     * Methode die den Spielraum und die Handlungsmöglichkeiten darin anzeigt
     *
     * @param stage             Stage zum anzeigen der Grafiken
     * @throws RemoteException  Fehler bei Datenübertragung
     */
    void displaySpielraum(Stage stage) throws RemoteException;
}
