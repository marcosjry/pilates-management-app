package com.user.managament.exception;

public class UserPasswordMismatchException extends RuntimeException{
    public UserPasswordMismatchException(String message) {
        super(message);
    }
}
