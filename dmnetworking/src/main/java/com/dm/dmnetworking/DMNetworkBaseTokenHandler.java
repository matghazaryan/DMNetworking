package com.dm.dmnetworking;

import android.content.Context;

import org.json.JSONObject;

public abstract class DMNetworkBaseTokenHandler {

    private String refreshTokenUrl;

    protected DMNetworkBaseTokenHandler(final String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    protected abstract void onTokenRefreshed(Context context, JSONObject jsonObject);

    protected abstract void onTokenRefreshFailure(Context context, JSONObject jsonObject);

    protected abstract void onNoInternetConnection(Context context);
}
