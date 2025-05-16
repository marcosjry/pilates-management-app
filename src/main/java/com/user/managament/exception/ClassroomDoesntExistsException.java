package com.user.managament.exception;

public class ClassroomDoesntExistsException extends RuntimeException{
    public ClassroomDoesntExistsException(String message) {
        super(message);
    }
}
