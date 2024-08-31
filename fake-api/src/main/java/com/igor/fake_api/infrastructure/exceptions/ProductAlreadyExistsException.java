package com.igor.fake_api.infrastructure.exceptions;

public class ProductAlreadyExistsException extends RuntimeException{

    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException() {
        super("This product is already registered");
    }

}
