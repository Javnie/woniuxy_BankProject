package com.woniuxy.exception;

public class CardNumAlreadyExistException extends RuntimeException{
    public CardNumAlreadyExistException() {
    }

    public CardNumAlreadyExistException(String message) {
        super(message);
    }

    public CardNumAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardNumAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
