package com.dm.dmnetworking.api_client.client;

import com.dm.dmnetworking.api_client.base.DMBaseAPIClient;
import com.dm.dmnetworking.api_client.base.DMBaseRequestConfig;
import com.dm.dmnetworking.api_client.listeners.DMIClientListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.entity.StringEntity;


public final class DMAPIClient extends DMBaseAPIClient {

    public static <T, E> void makeRequest(final DMBaseRequestConfig<T, E> config, final DMIClientListener listener) {

        showParams(config);

        final AsyncHttpResponseHandler handler = getHandler(config, listener);

        if (config.isJSONRequest()) {
            final StringEntity entity = setEntity(config.getJsonString());
            switch (config.getMethod()) {
                case GET:
                    getJSON(config, entity, handler);
                    break;
                case PUT:
                    putJSON(config, entity, handler);
                    break;
                case DELETE:
                    deleteJSON(config, entity, handler);
                    break;
                case POST:
                    postJSON(config, entity, handler);
                    break;
            }
        } else {
            switch (config.getMethod()) {
                case GET:
                    get(config, handler);
                    break;
                case PUT:
                    put(config, handler);
                    break;
                case DELETE:
                    delete(config, handler);
                    break;
                case POST:
                    post(config, handler);
                    break;
            }
        }
    }

    private static <T, E> void get(final DMBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).get(config.getUrl(), config.getRequestParams(), handler).setTag(config.getRequestTag());
    }

    private static <T, E> void post(final DMBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).post(config.getUrl(), config.getRequestParams(), handler).setTag(config.getRequestTag());
    }

    private static <T, E> void put(final DMBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).put(config.getUrl(), config.getRequestParams(), handler).setTag(config.getRequestTag());
    }

    private static <T, E> void delete(final DMBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        getClient(config.getContext()).delete(config.getUrl(), config.getRequestParams(), handler);
    }

    private static <T, E> void getJSON(final DMBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).get(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler).setTag(config.getRequestTag());
    }

    private static <T, E> void postJSON(final DMBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).post(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler).setTag(config.getRequestTag());
    }

    private static <T, E> void putJSON(final DMBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).put(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler).setTag(config.getRequestTag());
    }

    private static <T, E> void deleteJSON(final DMBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).delete(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler);
    }
}
