package com.dm.dmnetworking.api_client.base.model.error;

public final class ErrorE<E> {

    private int statusCode;

    private String status;

    private E e;

    public ErrorE(final int statusCode, final String status, final E e) {
        this.statusCode = statusCode;
        this.status = status;
        this.e = e;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public E getE() {
        return e;
    }
}
