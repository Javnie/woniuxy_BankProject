package com.woniuxy.exception;

public class UserAccountAlreadyExistException extends RuntimeException {
    public UserAccountAlreadyExistException() {
    }

    public UserAccountAlreadyExistException(String message) {
        super(message);
    }

    public UserAccountAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
