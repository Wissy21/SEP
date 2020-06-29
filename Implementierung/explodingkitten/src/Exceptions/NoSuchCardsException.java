package Exceptions;

public class NoSuchCardsException extends PlayException{
    public NoSuchCardsException() {
    }

    public NoSuchCardsException(String message) {
        super(message);
    }
}
