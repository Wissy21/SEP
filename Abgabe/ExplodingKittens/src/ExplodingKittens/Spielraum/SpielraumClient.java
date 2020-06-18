package ExplodingKittens.Spielraum;

import javafx.stage.Stage;

import java.rmi.RemoteException;

public interface SpielraumClient {

    void displaySpielraum(Stage stage) throws RemoteException;
}
