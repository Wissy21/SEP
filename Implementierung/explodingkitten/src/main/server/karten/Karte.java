package main.server.karten;

import java.io.Serializable;

public class Karte implements Serializable {
    String name;
    String effekt;

    /**
     * Erstellt eine neue Karte
     * @param name Name(ID) der Karte
     * @param effekt Effekt der Karte
     */
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


    /**
     * Angepasste equals Methode zum vergleichen von Karten
     * @param o Objekt zum vergleichen
     * @return true wenn ID und Effekt gleich, sonst false
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Karte)) {
            return false;
        }
        Karte k = (Karte) o;

        return k.getEffekt().equals(effekt)&&k.getName().equals(name);
    }
}

