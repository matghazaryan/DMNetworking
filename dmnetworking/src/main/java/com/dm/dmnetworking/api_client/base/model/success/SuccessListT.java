package com.dm.dmnetworking.api_client.base.model.success;

import java.util.List;

public final class SuccessListT<T> {

    private String status;

    private List<T> tList;

    public SuccessListT(final String status, final List<T> tList) {
        this.status = status;
        this.tList = tList;
    }

    public String getStatus() {
        return status;
    }

    public List<T> getList() {
        return tList;
    }
}
