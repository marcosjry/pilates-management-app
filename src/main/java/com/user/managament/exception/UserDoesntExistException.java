package com.user.managament.exception;

public class UserDoesntExistException extends RuntimeException{
    public UserDoesntExistException(String message) {
        super(message);
    }
}
