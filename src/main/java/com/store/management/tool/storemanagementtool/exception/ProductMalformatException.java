package com.store.management.tool.storemanagementtool.exception;

public class ProductMalformatException extends RuntimeException {

    public ProductMalformatException(String category) {
        super(String.format("Category name %s is not correct. It must contain only characters", category));
    }
}
