package com.user.managament.exception;

public class ContractDoesntExistsException extends RuntimeException{
    public ContractDoesntExistsException(String message) {
        super(message);
    }
}
