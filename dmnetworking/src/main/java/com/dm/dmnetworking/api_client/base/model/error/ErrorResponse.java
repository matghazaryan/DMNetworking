package com.dm.dmnetworking.api_client.base.model.error;

import org.json.JSONObject;

public final class ErrorResponse {

    private int statusCode;

    private String status;

    private JSONObject jsonObject;


    public ErrorResponse(final int statusCode, final String status, final JSONObject jsonObject) {
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
