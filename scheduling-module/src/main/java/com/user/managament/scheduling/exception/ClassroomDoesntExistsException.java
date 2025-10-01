package com.user.managament.scheduling.exception;

public class ClassroomDoesntExistsException extends RuntimeException{
    public ClassroomDoesntExistsException(String message) {
        super(message);
    }
}
