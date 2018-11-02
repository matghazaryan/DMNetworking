package com.dm.dmnetworking.api_client.base.model.success;

public final class SuccessT<T> {

    private int statusCode;

    private String status;

    private T t;

    public SuccessT(final int statusCode, final String status, final T t) {
        this.statusCode = statusCode;
        this.status = status;
        this.t = t;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public T getT() {
        return t;
    }
}
