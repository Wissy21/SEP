package main.exceptions;

public class SpielLauftBereitsException extends Exception{
    /**
     * Wird geworfen wenn versucht wird den SPielraum zu verändern, wenn das Spiel bereits läuft
     */
    public SpielLauftBereitsException() {
        super();
    }
}
