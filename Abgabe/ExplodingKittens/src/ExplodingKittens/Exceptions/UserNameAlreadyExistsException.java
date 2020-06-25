package main.exceptions;

public class UserNameAlreadyExistsException extends DataException{
    public UserNameAlreadyExistsException() {
    }

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
