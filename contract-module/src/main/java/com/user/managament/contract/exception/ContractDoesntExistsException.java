package com.user.managament.contract.exception;

public class ContractDoesntExistsException extends RuntimeException{
    public ContractDoesntExistsException(String message) {
        super(message);
    }
}
