package com.store.management.tool.storemanagementtool.exception;

public class ProductMalformatException extends RuntimeException {

    public ProductMalformatException(String category) {
        super(String.format("Trying to search by an invalid category = %s", category));
    }
}
