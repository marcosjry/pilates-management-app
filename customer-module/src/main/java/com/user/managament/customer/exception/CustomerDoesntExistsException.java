package com.user.managament.customer.exception;

public class CustomerDoesntExistsException extends RuntimeException{
    public CustomerDoesntExistsException(String message) {
        super(message);
    }
}
