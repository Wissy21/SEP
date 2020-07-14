package main.exceptions;

public class NichtGenugSpielerException extends Exception {
    /**
     * Wird geworfen wenn beim start des Spiels nicht genug Spieler im Raum sind
     */
    public NichtGenugSpielerException() {super();}
}
