package com.dm.dmnetworking.api_client.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dm.dmnetworking.BuildConfig;
import com.dm.dmnetworking.api_client.listeners.DMINetworkListener;
import com.dm.dmnetworking.parser.DMJsonParser;
import com.dm.dmnetworking.parser.DMParserConfigs;

import org.json.JSONObject;

import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;


abstract class DMBaseHelper extends DMBase {

    @SuppressLint("MissingPermission")
    private static boolean isConnectedToInternet(final Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {
                // connected to the internet
                switch (activeNetwork.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        return true;
                    case ConnectivityManager.TYPE_MOBILE:
                        return true;
                    default:
                        break;
                }
            }
        }
        return false;
    }

    final <T, E> void onFailure(final DMBaseRequestConfig<T, E> config, final int statusCode, final Throwable throwable, final JSONObject errorResponse, final DMINetworkListener<T, E> listener) {
        if (config != null) {
            if (BuildConfig.DEBUG) {
                Log.wtf(getTagForLogger(), "{ERROR==>>}{" + config.getUrl() + "}==>>" + errorResponse);
            }

            final String status;
            if (throwable instanceof UnknownHostException || throwable instanceof SocketException || throwable instanceof SocketTimeoutException) {
                listener.onNoInternetConnection();
            } else {
                status = String.valueOf(statusCode);
                listener.onError(statusCode, status, errorResponse);
                listener.onError(statusCode, errorResponse);
                try {
                    if (config.getErrorParserConfigs() != null && config.getErrorParserConfigs().getAClass() != null) {
                        listener.onError(statusCode, status, config.getErrorParserConfigs().getAClass().newInstance());
                    }
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    final void showLogs(final String pUrl, final JSONObject jsonObject, final File file) {
        if (BuildConfig.DEBUG && isEnableLogger()) {
            try {
                String logText = "";
                if (jsonObject != null) {
                    logText = jsonObject.toString();

                } else if (file != null) {
                    logText = file.getName();
                }
                Log.wtf(getTagForLogger(), "{OK}==>>{" + pUrl + "}==>>" + logText);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    final <T, E> void onParseComplete(final int statusCode, final String status, final JSONObject jsonObject, final DMParserConfigs<T> parserConfigs, final DMINetworkListener<T, E> listener) {
        if (parserConfigs != null && parserConfigs.getAClass() != null && parserConfigs.getJsonKeyList() != null) {
            switch (DMJsonParser.getType(jsonObject, parserConfigs.getJsonKeyList())) {
                case JSON_OBJECT:
                    listener.onComplete(statusCode, status, DMJsonParser.parseObject(jsonObject, parserConfigs.getAClass(), parserConfigs.getJsonKeyList()));
                    listener.onComplete(statusCode, status, new ArrayList<>());
                    break;
                case JSON_ARRAY:
                    listener.onComplete(statusCode, status, DMJsonParser.parseArray(jsonObject, parserConfigs.getAClass(), parserConfigs.getJsonKeyList()));
                    try {
                        listener.onComplete(statusCode, status, parserConfigs.getAClass().newInstance());
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        listener.onComplete(statusCode, status, jsonObject);
        listener.onComplete(statusCode, jsonObject);
    }

    final <T, E> void onErrorParse(final int statusCode, final String status, final JSONObject jsonObject, final DMParserConfigs<E> errorParserConfigs, final DMINetworkListener<T, E> listener) {
        if (errorParserConfigs != null && errorParserConfigs.getAClass() != null && errorParserConfigs.getJsonKeyList() != null) {
            switch (DMJsonParser.getType(jsonObject, errorParserConfigs.getJsonKeyList())) {
                case JSON_OBJECT:
                    listener.onError(statusCode, status, DMJsonParser.parseObject(jsonObject, errorParserConfigs.getAClass(), errorParserConfigs.getJsonKeyList()));
                    break;
                default:
                    try {
                        listener.onError(statusCode, status, errorParserConfigs.getAClass().newInstance());
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
            }
        }
        listener.onError(statusCode, jsonObject);
        listener.onError(statusCode, status, jsonObject);
    }

    public final void addHeaders(final Context context, final Map<String, String> headers) {
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();

            DMBaseAPIClient.getClient(context).addHeader(key, value);
        }
    }

    public final void removeAllHeaders(final Context context) {
        DMBaseAPIClient.getClient(context).removeAllHeaders();
    }

    public final void removeHeader(final Context context, final String header) {
        DMBaseAPIClient.getClient(context).removeHeader(header);
    }

    public final void cancelAllRequests(final Context context, final boolean mayInterruptIfRunning) {
        DMBaseAPIClient.getClient(context).cancelAllRequests(mayInterruptIfRunning);
    }

    public final void cancelRequestsByTag(final Context context, final String requestTag, final boolean mayInterruptIfRunning) {
        DMBaseAPIClient.getClient(context).cancelRequestsByTAG(requestTag, mayInterruptIfRunning);
    }

    protected boolean isNeedToMakeRequest(final Context context, final DMINetworkListener listener) {
        boolean isHasInternetConnection = true;

        if (!isConnectedToInternet(context)) {
            isHasInternetConnection = false;
            listener.onNoInternetConnection();
        }

        return isHasInternetConnection;
    }
}
