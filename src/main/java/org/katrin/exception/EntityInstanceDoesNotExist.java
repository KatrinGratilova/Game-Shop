package org.katrin.exception;

public class EntityInstanceDoesNotExist extends Exception {
    public EntityInstanceDoesNotExist(String message, Throwable cause){
        super(message, cause);
    }
}
