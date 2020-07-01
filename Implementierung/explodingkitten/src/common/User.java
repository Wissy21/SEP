package common;

import java.io.Serializable;

/**
 * Klasse die einen Benutzer darstellt
 */
public class User implements Serializable {
    private String name;

    /**
     * Konsatruktor für einen neuen Benutzer
     *
     * @param name      Name des Benutzers
     */
    public User(String name) {
        this.name = name;
    }

    /**
     *  Set Methode für den Benutzername
     *
     * @param name  Neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Methode für den Benutzername
     *
     * @return  Name wird ausgegeben
     */
    public String getName() {
        return name;
    }
}
