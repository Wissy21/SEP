package exceptions;

public class NotEqualPassWordException extends Exception {

    public NotEqualPassWordException(){super();};

    public NotEqualPassWordException(String message) {
        super(message);
    }
}
