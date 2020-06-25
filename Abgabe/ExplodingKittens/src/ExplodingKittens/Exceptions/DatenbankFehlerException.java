package Exceptions;

public class DatenbankFehlerException extends Exception {
    public DatenbankFehlerException(){
        super();
    }
    public DatenbankFehlerException(String message) {
        super(message);
    }
}
