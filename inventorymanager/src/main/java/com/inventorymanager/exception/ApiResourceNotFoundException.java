package com.inventorymanager.exception;

public class ApiResourceNotFoundException extends RuntimeException{

    public ApiResourceNotFoundException(String message) {
        super(message);
    }

}
