package ExplodingKittens.Exceptions;

public class UserNotExistException extends Exception {
    /**
     * Exception die geworfen wird,
     * wenn es auf dem Server keinen Benutzer mit diesem Namen gibt,
     * oder die Eingabe sonstig fehlerhaft ist.
     */
    public UserNotExistException(){}
}
