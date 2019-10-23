package com.dm.dmnetworking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


abstract class DMNetworkBaseHelper extends DMNetworkBase {

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

    final <T, E> void onFailure(final DMNetworkBaseRequestConfig<T, E> config, final int statusCode, final Throwable throwable, final JSONObject errorResponse, final DMNetworkIListener<T, E> listener) {
        if (config != null) {

            if (isEnableLogger()) {
                Log.wtf(getTagForLogger(), "{ERROR==>>}{" + config.getUrl() + "}==>>" + errorResponse);
            }

            if (throwable != null && throwable.getMessage() != null && throwable.getMessage().toLowerCase().contains("UnknownHostException".toLowerCase())) {
                listener.onNoInternetConnection();
                return;
            }

            if (throwable != null && throwable.getMessage() != null && throwable.getMessage().toLowerCase().contains("ConnectException".toLowerCase())) {
                listener.onNoInternetConnection();
                return;
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

    final void showLogs(final String pUrl, final JSONObject jsonObject, final File file, final JsonType jsonType) {
        if (isEnableLogger()) {

            final String fakeInfo = (jsonType == JsonType.FAKE_JSON) ? "={FAKE JSON}=" : "";

            try {
                String logText = "";
                if (jsonObject != null) {
                    logText = jsonObject.toString();

                } else if (file != null) {
                    logText = file.getName();
                }
                Log.wtf(getTagForLogger(), "{OK}=" + fakeInfo + "=>>{" + pUrl + "}==>>" + logText);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    final <T, E> void onParseComplete(final int statusCode, final String status, final JSONObject jsonObject, final DMNetworkParserConfigs<T> parserConfigs, final DMNetworkIListener<T, E> listener) {
        if (parserConfigs != null) {
            if (parserConfigs.getAClass() != null) {
                switch (DMNetworkJsonParser.getType(jsonObject, parserConfigs.getJsonKeyList())) {
                    case JSON_OBJECT:
                        listener.onComplete(statusCode, status, DMNetworkJsonParser.parseObject(jsonObject, parserConfigs.getAClass(), parserConfigs.getJsonKeyList()));
                        listener.onComplete(statusCode, status, new ArrayList<>());
                        listener.onComplete(statusCode, status, new HashMap<>());
                        break;
                    case JSON_ARRAY:
                        listener.onComplete(statusCode, status, new HashMap<>());
                        listener.onComplete(statusCode, status, DMNetworkJsonParser.parseArray(jsonObject, parserConfigs.getAClass(), parserConfigs.getJsonKeyList()));
                        try {
                            listener.onComplete(statusCode, status, parserConfigs.getAClass().newInstance());
                        } catch (IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } else {
                listener.onComplete(statusCode, status, (T) null);
                listener.onComplete(statusCode, status, new ArrayList<>());
                listener.onComplete(statusCode, status, DMNetworkJsonParser.parseMap(jsonObject, parserConfigs.getJsonKeyList()));
            }
        }
        listener.onComplete(statusCode, status, new HashMap<>());
        listener.onComplete(statusCode, status, jsonObject);
        listener.onComplete(statusCode, jsonObject);
    }

    final <T, E> void onErrorParse(final int statusCode, final String status, final JSONObject jsonObject, final DMNetworkParserConfigs<E> errorParserConfigs, final DMNetworkIListener<T, E> listener) {
        if (errorParserConfigs != null && errorParserConfigs.getAClass() != null && errorParserConfigs.getJsonKeyList() != null) {
            switch (DMNetworkJsonParser.getType(jsonObject, errorParserConfigs.getJsonKeyList())) {
                case JSON_OBJECT:
                    listener.onError(statusCode, status, DMNetworkJsonParser.parseObject(jsonObject, errorParserConfigs.getAClass(), errorParserConfigs.getJsonKeyList()));
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

            DMNetworkBaseAPIClient.getClient(context).addHeader(key, value);
        }
    }

    public final void removeAllHeaders(final Context context) {
        DMNetworkBaseAPIClient.getClient(context).removeAllHeaders();
    }

    public final void removeHeader(final Context context, final String header) {
        DMNetworkBaseAPIClient.getClient(context).removeHeader(header);
    }

    public final void cancelAllRequests(final Context context, final boolean mayInterruptIfRunning) {
        DMNetworkBaseAPIClient.getClient(context).cancelAllRequests(mayInterruptIfRunning);
    }

    public final void cancelRequestsByTag(final Context context, final String requestTag, final boolean mayInterruptIfRunning) {
        DMNetworkBaseAPIClient.getClient(context).cancelRequestsByTAG(requestTag, mayInterruptIfRunning);
    }

    protected boolean isNeedToMakeRequest(final Context context, final String url, final DMNetworkIListener listener) {
        boolean isHasInternetConnection = true;

        if (!isConnectedToInternet(context)) {
            isHasInternetConnection = false;
            listener.onNoInternetConnection();
        }

        return isHasInternetConnection;
    }

    @Override
    protected void beforeRequest(final Context context, final String url, final DMNetworkRequestListener listener) {
        listener.doRequest();
    }

    void tryToLoadJSONFromAsset(final Context context, final String filePath, final DMNetworkingIOnReadFileListener listener) {
        if (isEnableFakeJson() && filePath != null) {
            Executors.newSingleThreadExecutor().execute(() -> {

                JSONObject jsonObject = null;

                String json = null;
                try {
                    final InputStream is = context.getAssets().open(filePath);
                    final int size = is.available();
                    final byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, StandardCharsets.UTF_8);
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }

                if (json != null) {
                    try {
                        jsonObject = new JSONObject(json);
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                }


                final JSONObject finalJsonObject = jsonObject;
                new Handler(Looper.getMainLooper()).post(() -> listener.onRead(finalJsonObject));
            });
        } else {
            listener.onRead(null);
        }
    }
}
