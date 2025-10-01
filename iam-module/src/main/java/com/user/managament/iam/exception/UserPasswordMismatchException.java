package com.user.managament.iam.exception;

public class UserPasswordMismatchException extends RuntimeException{
    public UserPasswordMismatchException(String message) {
        super(message);
    }
}
