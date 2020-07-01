package exceptions;

public class PasswortFalschException extends DatenbankFehlerException {


    public PasswortFalschException(){
        super();
    }

    public PasswortFalschException(String message) {
        super(message);
    }

}
