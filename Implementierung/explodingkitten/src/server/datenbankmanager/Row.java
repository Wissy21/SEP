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

    public Row(int platz,int punkte,String name) {
        this.name = name;
        this.platz = platz;
        this.punkte = punkte;
    }

    public StringProperty nameProperty() {
        return  new SimpleStringProperty(name);
    }

    public IntegerProperty platzProperty() {
        return new SimpleIntegerProperty(platz);
    }

    public IntegerProperty punkteProperty() {
        return new SimpleIntegerProperty(punkte);
    }
}
