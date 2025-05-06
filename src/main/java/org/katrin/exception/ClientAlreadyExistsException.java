package org.katrin.exception;

public class ClientAlreadyExistsException extends Exception {
    public ClientAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

