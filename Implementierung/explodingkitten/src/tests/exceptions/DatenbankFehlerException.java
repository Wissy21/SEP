package tests.exceptions;

public class DatenbankFehlerException extends Exception {
    public DatenbankFehlerException(){
        super();
    }
    public DatenbankFehlerException(String message) {
        super(message);
    }
}
