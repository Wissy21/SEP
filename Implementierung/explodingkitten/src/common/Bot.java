package common;

/**
 * Klasse die einen Bot-Mensch darstellt
 */
public class Bot extends User{
    private String name;

    /**
     * Konstruktor für einen Bot-Mensch
     *
     * @param name  Name des Bots
     */
    public Bot(String name) {
        super(name);
    }
}
