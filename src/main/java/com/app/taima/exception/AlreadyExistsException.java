package com.app.taima.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String desc) {
        super(desc);
    }
}
