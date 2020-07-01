package exceptions;

import java.rmi.RemoteException;


public class KeineAntwortException extends RemoteException {

    public KeineAntwortException(){
        super();
    }


    public KeineAntwortException(String message) {
        super(message);
    }

}
