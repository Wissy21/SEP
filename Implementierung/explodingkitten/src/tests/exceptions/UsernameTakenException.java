package tests.exceptions;

public class UsernameTakenException extends Exception{
    /**
     * Exception die geworfen wird,
     * wenn bei einer neuen Registrierung der gewünschte Name bereits vergeben ist.
     */
    public UsernameTakenException(){}
}
