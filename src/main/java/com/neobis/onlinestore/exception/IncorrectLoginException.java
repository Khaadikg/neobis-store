package com.neobis.onlinestore.exception;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException(String message) {
        super(message);
    }
}
