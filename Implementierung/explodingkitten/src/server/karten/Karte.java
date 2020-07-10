package server.karten;

import java.io.Serializable;

public class Karte implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Karte)) {
            return false;
        }
        Karte k = (Karte) o;

        return k.getEffekt().equals(effekt)&&k.getName().equals(name);
    }
}

