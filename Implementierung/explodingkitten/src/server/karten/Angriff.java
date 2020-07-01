package server.karten;

public class Angriff extends server.karten.Karte {
    String name;
    String effekt;

    public Angriff(String name, String effekt) {
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
}
