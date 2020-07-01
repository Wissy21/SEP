package common;

/**
 * Klasse die einen Bot-Spieler darstellt
 */
public class Bot extends User{
    private String name;

    /**
     * Konstruktor für einen Bot-Spieler
     *
     * @param name  Name des Bots
     */
    public Bot(String name) {
        super(name);
    }
}
