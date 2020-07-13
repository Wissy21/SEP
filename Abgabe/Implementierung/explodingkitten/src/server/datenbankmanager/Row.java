package server.datenbankmanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Row implements Serializable {
    int platz;
    int punkte;
    String name;

    /**
     * Konstruktor für eine neue Zeile
     * @param platz Platz des Spielers
     * @param punkte Anzahl der Siege des Spielers
     * @param name Name des Spielers
     */
    public Row(int platz,int punkte,String name) {
        this.name = name;
        this.platz = platz;
        this.punkte = punkte;
    }

    /**
     * Gibt den Namen so zurück, dass er in der GUI Bestenliste angezeigt werden kann
     * @return Name
     */
    public StringProperty nameProperty() {
        return  new SimpleStringProperty(name);
    }
    /**
     * Gibt den Platz so zurück, dass er in der GUI Bestenliste angezeigt werden kann
     * @return Name
     */
    public IntegerProperty platzProperty() {
        return new SimpleIntegerProperty(platz);
    }
    /**
     * Gibt die Punkte so zurück, dass sie in der GUI Bestenliste angezeigt werden können
     * @return Name
     */
    public IntegerProperty punkteProperty() {
        return new SimpleIntegerProperty(punkte);
    }
}
