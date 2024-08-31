package com.igor.fake_api.infrastructure.exceptions;

public class ProductUnexistException extends RuntimeException {

    public ProductUnexistException(String message) {
        super(message);
    }

    public ProductUnexistException() {
        super("This product does not exist");
    }

}
