package main.exceptions;

public class WrongPassWordException extends DataException {
    public WrongPassWordException() {
    }

    public WrongPassWordException(String message) {
        super(message);
    }
}
