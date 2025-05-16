package com.user.managament.exception;

public class CustomerDoesntExistsException extends RuntimeException{
    public CustomerDoesntExistsException(String message) {
        super(message);
    }
}
