package com.dm.dmnetworking.model.success;

import java.util.Map;

public final class SuccessMapT<T> {

    private int statusCode;

    private String status;

    private Map<String, T> tMap;

    public SuccessMapT(final int statusCode, final String status, final Map<String, T> tMap) {
        this.statusCode = statusCode;
        this.status = status;
        this.tMap = tMap;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, T> getMap() {
        return tMap;
    }
}
