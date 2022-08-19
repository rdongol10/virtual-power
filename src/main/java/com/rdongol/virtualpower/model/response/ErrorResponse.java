package com.rdongol.virtualpower.model.response;

import java.util.List;

public class ErrorResponse implements Response {

    private int status;
    private String message;
    private List<String> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

}
