package main.exceptions;

public class NotEqualPassWordException extends Exception {
    /**
     * Wird geworfen wenn das angegebene Passwort nicht mit dem in der Datenbank gespeicherten Passwort übereinstimmt
     */
    public NotEqualPassWordException(){super();}
}
