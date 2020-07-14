package main.exceptions;

public class NotYourRundeException extends PlayException{
    /**
     * Wird geworfen wenn man versucht eine Karte zu spielen, aber nicht an der Runde ist
     */
    public NotYourRundeException() { super();}

}
