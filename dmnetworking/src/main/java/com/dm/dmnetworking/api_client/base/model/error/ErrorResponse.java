package com.dm.dmnetworking.api_client.base.model.error;

import org.json.JSONObject;

public final class ErrorResponse {

    private String status;

    private JSONObject jsonObject;


    public ErrorResponse(final String status, final JSONObject jsonObject) {
        this.status = status;
        this.jsonObject = jsonObject;
    }

    public String getStatus() {
        return status;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
