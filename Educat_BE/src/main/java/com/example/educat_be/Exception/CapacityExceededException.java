package com.example.educat_be.Exception;



public class CapacityExceededException extends RuntimeException {

    public CapacityExceededException(String message) {
        super(message);
    }

    public CapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
