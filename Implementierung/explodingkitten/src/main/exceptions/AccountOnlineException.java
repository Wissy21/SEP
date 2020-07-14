package main.exceptions;


public class AccountOnlineException extends Exception {
    /**
     * Wird geworfen wenn beim Anmelden bereits jemand mit den angegebenen Daten angemeldet ist
     */
    public AccountOnlineException() {
        super();
    }
}
