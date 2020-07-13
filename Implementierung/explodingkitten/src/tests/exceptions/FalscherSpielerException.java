package tests.exceptions;

import java.io.IOException;


public class FalscherSpielerException extends IOException {


    public FalscherSpielerException(){
        super();
    }

    public FalscherSpielerException(String message) {
        super(message);
    }

}

