package tests.exceptions;

public class  WrongPasswordException extends Exception{
    /**
     * Exception die geworfen wird,
     * wenn beim Anmelden ein richtiger Benutzernamen, aber das falsche Passwort angegeben wird.
     */
    public WrongPasswordException(){
    }
}
