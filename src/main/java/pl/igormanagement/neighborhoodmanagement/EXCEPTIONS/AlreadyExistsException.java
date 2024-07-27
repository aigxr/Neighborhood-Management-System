package pl.igormanagement.neighborhoodmanagement.EXCEPTIONS;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
