package ExplodingKittens.Login;


import javafx.stage.Stage;

/**
 * Interface die die Methoden  zur Darstellung des Clients zur Anmeldung/Registrierung bereitstellt
 */
public interface LoginClient {

    /**
     * Methode die den Auswahlbildschirm zwischen Anmelden und Registrieren erstellt und anzeigt
     *
     * @param stage Stage wird zum Anzeigen der erstellten Grafik übergeben
     */
    public void selection(Stage stage);

    /**
     * Methode die das Loginfenster erstellt und anzeigt
     *
     * @param stage Stage wird zum Anzeigen der erstellten Grafik übergeben
     */
    public void loginGUI(Stage stage);

    /**
     * Methode die das Registrierungsfenster erstellt und anzeigt
     *
     * @param stage Stage wird zum Anzeigen der erstellten Grafik übergeben
     */
    public void registerGUI(Stage stage);
}
