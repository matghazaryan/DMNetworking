package com.dm.dmnetworking.api_client.base.model.success;

import java.util.List;

public final class SuccessListT<T> {

    private int statusCode;

    private String status;

    private List<T> tList;

    public SuccessListT(final int statusCode, final String status, final List<T> tList) {
        this.statusCode = statusCode;
        this.status = status;
        this.tList = tList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public List<T> getList() {
        return tList;
    }
}
