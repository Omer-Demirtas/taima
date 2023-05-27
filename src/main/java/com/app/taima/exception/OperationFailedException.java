package com.app.taima.exception;

public class OperationFailedException extends RuntimeException {
    public OperationFailedException(String desc) {
        super(desc);
    }
}
