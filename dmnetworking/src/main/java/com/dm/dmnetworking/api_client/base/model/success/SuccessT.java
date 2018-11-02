package com.dm.dmnetworking.api_client.base.model.success;

public final class SuccessT<T> {

    private String status;

    private T t;

    public SuccessT(final String status, final T t) {
        this.status = status;
        this.t = t;
    }

    public String getStatus() {
        return status;
    }

    public T getT() {
        return t;
    }
}
