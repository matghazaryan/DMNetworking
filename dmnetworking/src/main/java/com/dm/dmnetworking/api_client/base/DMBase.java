package com.dm.dmnetworking.api_client.base;

import android.content.Context;

import com.dm.dmnetworking.api_client.listeners.DMIStatusHandleListener;

import org.json.JSONObject;


abstract class DMBase implements DMIBaseMethod {

    protected abstract void handleStatuses(final Context context, final int statusCode, final JSONObject jsonObject, final DMIStatusHandleListener listener);

    protected abstract int getRequestTimeOut();

    protected abstract String getFullUrl(final String methodUrl);

    abstract void requestForToken(final int statusCode, final Context context, final DMBaseTokenHandler baseToken, final DMIBaseOnTokenRefreshListener listener);
}
