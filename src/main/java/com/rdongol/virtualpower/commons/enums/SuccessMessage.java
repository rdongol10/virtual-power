package com.rdongol.virtualpower.commons.enums;

public enum SuccessMessage {

    SAVED("SAV001", "Saved successfully"),
    SEARCHED("SER001", "Search successful");

    SuccessMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
