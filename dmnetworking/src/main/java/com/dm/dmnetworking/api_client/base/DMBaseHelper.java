package com.dm.dmnetworking.api_client.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dm.dmnetworking.BuildConfig;
import com.dm.dmnetworking.api_client.listeners.DMINetworkListener;
import com.dm.dmnetworking.parser.DMJsonParser;
import com.dm.dmnetworking.parser.DMParserConfigs;

import org.json.JSONObject;

import java.util.Map;


abstract class DMBaseHelper extends DMBase {

    private boolean isConnectedToInternet(final Context context) {
        final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            final NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (final NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }

        return false;
    }

    final void onFailure(final String pUrl, int statusCode, final Throwable throwable, final JSONObject errorResponse, final DMINetworkListener pListener) {
        if (BuildConfig.DEBUG) {
            Log.wtf(getTagForLogger(), "{ERROR==>>}{" + pUrl + "}==>>" + errorResponse);
        }

        final String status;
        if (throwable instanceof Exception) {
            pListener.onNoInternetConnection();
        } else {
            status = String.valueOf(statusCode);
            pListener.onError(status, errorResponse);
        }


    }

    final void showLogs(final String pUrl, final JSONObject jsonObject) {
        if (BuildConfig.DEBUG && isEnableLogger()) {
            try {
                Log.wtf(getTagForLogger(), "{OK}==>>{" + pUrl + "}==>>" + jsonObject);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    final <T, E> void onParseComplete(final String status, final JSONObject jsonObject, final DMParserConfigs<T> parserConfigs, final DMINetworkListener<T, E> listener) {
        if (parserConfigs != null && parserConfigs.getAClass() != null && parserConfigs.getJsonKeyList() != null) {
            switch (DMJsonParser.getType(jsonObject, parserConfigs.getJsonKeyList())) {
                case JSON_OBJECT:
                    listener.onComplete(status, DMJsonParser.parseObject(jsonObject, parserConfigs.getAClass(), parserConfigs.getJsonKeyList()));
                    break;
                case JSON_ARRAY:
                    listener.onComplete(status, DMJsonParser.parseArray(jsonObject, parserConfigs.getAClass(), parserConfigs.getJsonKeyList()));
                    break;
            }
        }
        listener.onComplete(status, jsonObject);
        listener.onComplete(jsonObject);
    }

    final <T, E> void onErrorParse(final String status, final JSONObject jsonObject, final DMParserConfigs<E> errorParserConfigs, final DMINetworkListener<T, E> listener) {
        if (errorParserConfigs != null && errorParserConfigs.getAClass() != null && errorParserConfigs.getJsonKeyList() != null) {
            switch (DMJsonParser.getType(jsonObject, errorParserConfigs.getJsonKeyList())) {
                case JSON_OBJECT:
                    listener.onError(status, DMJsonParser.parseObject(jsonObject, errorParserConfigs.getAClass(), errorParserConfigs.getJsonKeyList()));
                    break;
            }
        }
        listener.onError(jsonObject);
        listener.onError(status, jsonObject);
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

    protected boolean isNeedToMakeRequest(final Context context, final DMINetworkListener pListener) {
        boolean isHasInternetConnection = true;

        if (!isConnectedToInternet(context)) {
            isHasInternetConnection = false;
            pListener.onNoInternetConnection();
        }

        return isHasInternetConnection;
    }
}
