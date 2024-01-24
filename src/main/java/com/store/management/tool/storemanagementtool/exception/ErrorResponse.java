package com.store.management.tool.storemanagementtool.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class ErrorResponse {
    private final int statusCode;
    private final String message;
    private List<ValidationError> errors;

    @Data
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)) errors = new ArrayList<>();
        errors.add(new ValidationError(field, message));
    }
}
