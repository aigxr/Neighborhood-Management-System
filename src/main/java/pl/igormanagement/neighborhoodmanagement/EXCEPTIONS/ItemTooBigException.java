package pl.igormanagement.neighborhoodmanagement.EXCEPTIONS;

public class ItemTooBigException extends RuntimeException{
    public ItemTooBigException(String message) {
        super(message);
    }
}
