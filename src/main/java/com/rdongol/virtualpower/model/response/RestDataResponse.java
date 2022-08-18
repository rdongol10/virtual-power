package com.rdongol.virtualpower.model.response;

public class RestDataResponse<T extends Response> extends RestResponse {
    private T data;

    public RestDataResponse(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
