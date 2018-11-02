package com.dm.dmnetworking.api_client.base;

import android.content.Context;

import org.json.JSONObject;

public abstract class DMBaseTokenHandler {

    private String refreshTokenUrl;

    protected DMBaseTokenHandler(final String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    protected abstract void onTokenRefreshed(Context context, JSONObject jsonObject);

    protected abstract void onTokenRefreshFailure(Context context, JSONObject jsonObject);

    protected abstract void onNoInternetConnection(Context context);
}
