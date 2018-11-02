package com.dm.dmnetworking.api_client.base.model.error;

public final class ErrorE<E> {

    private String status;

    private E e;

    public ErrorE(final String status, final E e) {
        this.status = status;
        this.e = e;
    }

    public String getStatus() {
        return status;
    }

    public E getE() {
        return e;
    }
}
