package tests.exceptions;

public class DataNotMatchException extends exceptions.DataException {
    public DataNotMatchException() {
    }

    public DataNotMatchException(String message) {
        super(message);
    }
}
