package com.rdongol.virtualpower.exception_handler.exception;

import java.util.List;

public class UnProcessableEntityException extends RuntimeException {

    private List<String> errors;

    public UnProcessableEntityException(List<String> errors, String message) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
