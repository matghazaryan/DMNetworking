package com.dm.dmnetworking.api_client.base.model.success;

import org.json.JSONObject;

public final class SuccessResponse {

    private String status;

    private JSONObject jsonObject;


    public SuccessResponse(final String status, final JSONObject jsonObject) {
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
