package com.dm.dmnetworking.model.success;

import org.json.JSONObject;

public final class SuccessResponse {

    private int statusCode;

    private String status;

    private JSONObject jsonObject;


    public SuccessResponse(final int statusCode, final String status, final JSONObject jsonObject) {
        this.statusCode = statusCode;
        this.status = status;
        this.jsonObject = jsonObject;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
