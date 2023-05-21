package me.vegura.resourceprocessor.exceptions;

public class ResourceFetchException extends RuntimeException {
    public ResourceFetchException(String message) {
        super(message);
    }

    public ResourceFetchException() {
        super();
    }
}
