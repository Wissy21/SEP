package main.exceptions;

public class NotYourRundeException extends PlayException{
    public NotYourRundeException() {
    }

    public NotYourRundeException(String message) {
        super(message);
    }
}
