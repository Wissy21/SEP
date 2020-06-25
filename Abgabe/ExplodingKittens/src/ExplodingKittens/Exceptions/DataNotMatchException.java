package main.exceptions;

public class DataNotMatchException extends DataException{
    public DataNotMatchException() {
    }

    public DataNotMatchException(String message) {
        super(message);
    }
}
