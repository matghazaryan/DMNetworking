package com.dm.dmnetworking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.dm.dmnetworking.model.progress.FileProgress;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


abstract class DMNetworkBaseAPIClient implements DMNetworkIConstants {

    private static AsyncHttpClient client;
    static RequestHandle requestHandler;
    private static int requestTimeOut;
    private static String tag;
    private static boolean isEnableLogger;

    static AsyncHttpClient getClient(final Context context) {

        if (client == null) {
            client = new AsyncHttpClient();
            client.setEnableRedirects(true);
            client.setCookieStore(new PersistentCookieStore(context));
        }

        client.setTimeout(requestTimeOut);
        client.setConnectTimeout(requestTimeOut);
        client.setResponseTimeout(requestTimeOut);

        return client;
    }

    static void setRequestTimeOut(final int requestTimeOut) {
        DMNetworkBaseAPIClient.requestTimeOut = requestTimeOut;
    }

    static void setEnableLogger(final boolean isEnableLogger) {
        DMNetworkBaseAPIClient.isEnableLogger = isEnableLogger;
    }

    public static void setTag(final String tag) {
        DMNetworkBaseAPIClient.tag = tag;
    }

    static <T, E> void showParams(final DMNetworkBaseRequestConfig<T, E> config) {
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

    private static void onProgressHandle(final long bytesWritten, final long totalSize, final DMNetworkIClientListener listener) {
        @SuppressLint("DefaultLocale") final String percentString = String.format("%2.0f%%", (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1);
        @SuppressLint("DefaultLocale") final int percent = Integer.parseInt(String.format("%2.0f", (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        listener.onFileProgress(new FileProgress(bytesWritten, totalSize, percent, percentString));
    }

    private static void onFailureHandler(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable, final DMNetworkIClientListener listener) {
        try {
            listener.onFailure(statusCode, headers, throwable, new JSONObject()
                    .put(STATUS_CODE, statusCode)
                    .put(ERROR, throwable.getMessage())
                    .put(RESPONSE_STRING, responseString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static StringEntity setEntity(final String jsonString) {
        StringEntity entity = null;
        if (jsonString != null) {
            entity = new StringEntity(jsonString, UTF_8);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            entity.setContentType(APPLICATION_JSON);
        }

        return entity;
    }

    static <T, E> AsyncHttpResponseHandler getHandler(final DMNetworkBaseRequestConfig<T, E> config, final DMNetworkIClientListener listener) {
        final AsyncHttpResponseHandler handler;
        if (config.isEnableDownload()) {
            handler = new FileAsyncHttpResponseHandler(config.getContext()) {
                @Override
                public void onSuccess(final int statusCode, final Header[] headers, final File file) {
                    listener.onComplete(statusCode, headers, null, file);
                }

                @Override
                public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final File file) {
                    onFailureHandler(statusCode, headers, null, throwable, listener);
                }

                @Override
                public void onProgress(final long bytesWritten, final long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    onProgressHandle(bytesWritten, totalSize, listener);
                }
            };
        } else {
            handler = new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(final int statusCode, final Header[] headers, final JSONObject jsonObject) {
                    listener.onComplete(statusCode, headers, jsonObject, null);
                }

                @Override
                public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                    listener.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
                    onFailureHandler(statusCode, headers, responseString, throwable, listener);
                }

                @Override
                public void onProgress(final long bytesWritten, final long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    onProgressHandle(bytesWritten, totalSize, listener);
                }
            };
        }

        return handler;
    }
}
