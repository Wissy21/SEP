package main.server.karten;

public class Karte {
    String name;
    String effekt;

    public Karte(String name, String effekt) {
        this.name = name;
        this.effekt = effekt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffekt() {
        return effekt;
    }

    public void setEffekt(String effekt) {
        this.effekt = effekt;
    }

<<<<<<< HEAD
    private boolean effekt_ausfuehren() {
=======
    private boolean effektAusfuehren() {
>>>>>>> maxime
        return false;
    }
}
