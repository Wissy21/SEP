package Exceptions;

public class BenutzerExistiertBereitsException extends RuntimeException {

    public BenutzerExistiertBereitsException(String message){
        super(message);
    }
}
