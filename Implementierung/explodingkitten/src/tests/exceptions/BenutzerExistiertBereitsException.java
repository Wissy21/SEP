package tests.exceptions;

public class BenutzerExistiertBereitsException extends RuntimeException {

    public BenutzerExistiertBereitsException(String message){
        super(message);
    }
}
