package main.exceptions;

public class SpielraumVollException  extends Exception{
    /**
     * Wird geworfen wenn dem Raum ein Spieler hinzugefügt werden soll, der Raum aber schon voll ist
     */
    public SpielraumVollException(){
        super();
    }
}

