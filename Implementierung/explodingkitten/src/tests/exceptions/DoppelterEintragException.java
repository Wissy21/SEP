package tests.exceptions;


public class DoppelterEintragException extends DatenbankFehlerException {

    public DoppelterEintragException(){
        super();
    }


    public DoppelterEintragException(String message) {
        super(message);
    }

}
