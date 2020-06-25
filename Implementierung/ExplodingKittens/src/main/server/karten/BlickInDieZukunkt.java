package main.server.karten;

<<<<<<< HEAD
public class BlickInDieZukunkt {
=======
public class BlickInDieZukunkt extends Karte {
    String name;
    String effekt;

    public BlickInDieZukunkt(String name, String effekt) {
        super(name, effekt);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEffekt() {
        return effekt;
    }

    @Override
    public void setEffekt(String effekt) {
        this.effekt = effekt;
    }

    private boolean effektAusfuehren() {
        return false;
    }
>>>>>>> maxime
}
