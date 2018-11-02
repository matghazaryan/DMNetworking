package com.dm.dmnetworking.api_client.base;

import android.content.Context;

import com.dm.dmnetworking.api_client.listeners.DMIStatusHandleListener;

import org.json.JSONObject;


abstract class DMBase implements DMIBaseMethod {

    protected abstract void handleStatuses(final Context context, final JSONObject jsonObject, final DMIStatusHandleListener listener);

    protected abstract int getRequestTimeOut();

    abstract void requestForToken(final Context context, final DMBaseTokenHandler baseToken, final DMIBaseOnTokenRefreshListener listener);
}
