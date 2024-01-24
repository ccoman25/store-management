package com.store.management.tool.storemanagementtool.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Integer id) {
        super(String.format("Product %s was not found", id));
    }
}
