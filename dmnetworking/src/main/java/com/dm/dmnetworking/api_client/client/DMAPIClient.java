package com.dm.dmnetworking.api_client.client;

import com.dm.dmnetworking.api_client.base.DMBaseAPIClient;
import com.dm.dmnetworking.api_client.base.DMBaseRequestConfig;
import com.dm.dmnetworking.api_client.listeners.DMIClientListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public final class DMAPIClient extends DMBaseAPIClient {

    public static <T, E> void makeRequest(final DMBaseRequestConfig<T, E> config, final DMIClientListener pListener) {

        showParams(config);

        switch (config.getMethod()) {
            case GET:
                get(config, pListener);
                break;
            case PUT:
                put(config, pListener);
                break;
            case DELETE:
                delete(config, pListener);
                break;
            case POST:
                post(config, pListener);
                break;
        }
    }

    private static <T, E> void get(final DMBaseRequestConfig<T, E> config, final DMIClientListener pListener) {

        requestHandler = getClient(config.getContext()).get(config.getUrl(), config.getRequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject jsonObject) {
                pListener.onComplete(statusCode, headers, jsonObject);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                pListener.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
                onFailureHandler(statusCode, headers, responseString, throwable, pListener);
            }
        }).setTag(config.getRequestTag());
    }

    private static <T, E> void post(final DMBaseRequestConfig<T, E> config, final DMIClientListener pListener) {

        requestHandler = getClient(config.getContext()).post(config.getUrl(), config.getRequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject jsonObject) {
                pListener.onComplete(statusCode, headers, jsonObject);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                pListener.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
                onFailureHandler(statusCode, headers, responseString, throwable, pListener);
            }
        }).setTag(config.getRequestTag());
    }

    private static <T, E> void put(final DMBaseRequestConfig<T, E> config, final DMIClientListener pListener) {

        requestHandler = getClient(config.getContext()).put(config.getUrl(), config.getRequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject jsonObject) {
                pListener.onComplete(statusCode, headers, jsonObject);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                pListener.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
                onFailureHandler(statusCode, headers, responseString, throwable, pListener);
            }
        }).setTag(config.getRequestTag());
    }

    private static <T, E> void delete(final DMBaseRequestConfig<T, E> config, final DMIClientListener pListener) {

        getClient(config.getContext()).delete(config.getUrl(), config.getRequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject jsonObject) {
                pListener.onComplete(statusCode, headers, jsonObject);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                pListener.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
                onFailureHandler(statusCode, headers, responseString, throwable, pListener);
            }
        });
    }
}
