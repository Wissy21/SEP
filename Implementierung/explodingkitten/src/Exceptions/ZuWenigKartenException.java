package Exceptions;

public class ZuWenigKartenException extends Exception {

    /**
     *  Wenn der Spieler nicht genügend Karten hat um die Spezial Aktion einer Karte spielen
     * zu können wird die Exception geworfen.
     */
    public ZuWenigKartenException(){
        super();
    }

    /**
     *
     * @param message
     */
    public ZuWenigKartenException(String message) {
        super(message);
    }

}
