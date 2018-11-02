package com.dm.dmnetworking.api_client.base;

import android.content.Context;
import android.util.Log;

import com.dm.dmnetworking.api_client.constants.DMINetworkingConstants;
import com.dm.dmnetworking.api_client.listeners.DMIClientListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public abstract class DMBaseAPIClient implements DMINetworkingConstants {

    private static AsyncHttpClient client;
    protected static RequestHandle requestHandler;
    private static int requestTimeOut;
    private static String tag;
    private static boolean isEnableLogger;

    protected static AsyncHttpClient getClient(final Context context) {
        if (client == null) {
            client = new AsyncHttpClient();
            client.setEnableRedirects(true);
            client.setCookieStore(new PersistentCookieStore(context));
            client.setTimeout(requestTimeOut);
        }

        return client;
    }

    public static void setRequestTimeOut(final int requestTimeOut) {
        DMBaseAPIClient.requestTimeOut = requestTimeOut;
    }

    public static void setEnableLogger(final boolean isEnableLogger) {
        DMBaseAPIClient.isEnableLogger = isEnableLogger;
    }

    public static void setTag(final String tag) {
        DMBaseAPIClient.tag = tag;
    }

    protected static <T, E> void showParams(final DMBaseRequestConfig<T, E> config) {
        if (isEnableLogger) {
            Log.wtf(tag, "___________________________________________________");
            Log.wtf(tag, URL + config.getUrl());
            if (config.getParams() != null) {
                for (final Map.Entry<String, Object> entry : config.getParams().entrySet()) {
                    Log.wtf(tag, entry.getKey() + "---->>>>" + entry.getValue());
                }
            } else if (config.getJsonString() != null) {
                Log.wtf(tag, config.getJsonString());
            }
            Log.wtf(tag, "___________________________________________________");
        }
    }

    protected static void onFailureHandler(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable, final DMIClientListener listener) {
        try {
            listener.onFailure(statusCode, headers, throwable, new JSONObject()
                    .put(STATUS_CODE, statusCode)
                    .put(ERROR, throwable.getMessage())
                    .put(RESPONSE_STRING, responseString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected static StringEntity setEntity(final String jsonString) {
        StringEntity entity = null;
        if (jsonString != null) {
            entity = new StringEntity(jsonString, UTF_8);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            entity.setContentType(APPLICATION_JSON);
        }

        return entity;
    }
}
