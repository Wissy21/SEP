package main.exceptions;

public class UserNotExistException extends DataException{
    public UserNotExistException() {
    }

    public UserNotExistException(String message) {
        super(message);
    }
}
