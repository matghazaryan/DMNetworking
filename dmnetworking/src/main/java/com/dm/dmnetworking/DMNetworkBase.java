package com.dm.dmnetworking;

import android.content.Context;

import org.json.JSONObject;


abstract class DMNetworkBase implements DMNetworkIBaseMethod {

    protected abstract void handleStatuses(final Context context, final int statusCode, final JSONObject jsonObject, final DMNetworkIStatusHandleListener listener);

    protected abstract int getRequestTimeOut();

    protected abstract String getFullUrl(final Context context, final String url);

    protected abstract void beforeRequest(final Context context, final String url, final DMNetworkRequestListener listener);

    abstract void requestForToken(final int statusCode, final Context context, final DMNetworkBaseTokenHandler baseToken, final DMNetworkIBaseOnTokenRefreshListener listener);
}
