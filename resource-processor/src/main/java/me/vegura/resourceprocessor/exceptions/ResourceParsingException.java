package me.vegura.resourceprocessor.exceptions;

public class ResourceParsingException extends RuntimeException {
    public ResourceParsingException(String message) {
        super(message);
    }

    public ResourceParsingException() {
        super();
    }

    public ResourceParsingException(Exception ex) {
        super(ex);
    }
}
