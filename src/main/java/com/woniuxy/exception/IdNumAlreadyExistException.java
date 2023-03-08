package com.woniuxy.exception;

public class IdNumAlreadyExistException extends RuntimeException{
    public IdNumAlreadyExistException() {

    }

    public IdNumAlreadyExistException(String message) {
        super(message);
    }

    public IdNumAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNumAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
