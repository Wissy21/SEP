package Exceptions;

public class DataNotMatchException extends Exceptions.DataException {
    public DataNotMatchException() {
    }

    public DataNotMatchException(String message) {
        super(message);
    }
}
