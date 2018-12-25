package com.dm.dmnetworking;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.entity.StringEntity;


final class DMNetworkAPIClient extends DMNetworkBaseAPIClient {

    static <T, E> void makeRequest(final DMNetworkBaseRequestConfig<T, E> config, final DMNetworkIClientListener listener) {

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

    private static <T, E> void get(final DMNetworkBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).get(config.getUrl(), config.getRequestParams(), handler).setTag(config.getRequestTag());
    }

    private static <T, E> void post(final DMNetworkBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).post(config.getUrl(), config.getRequestParams(), handler).setTag(config.getRequestTag());
    }

    private static <T, E> void put(final DMNetworkBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).put(config.getUrl(), config.getRequestParams(), handler).setTag(config.getRequestTag());
    }

    private static <T, E> void delete(final DMNetworkBaseRequestConfig<T, E> config, final AsyncHttpResponseHandler handler) {
        getClient(config.getContext()).delete(config.getUrl(), config.getRequestParams(), handler);
    }

    private static <T, E> void getJSON(final DMNetworkBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).get(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler).setTag(config.getRequestTag());
    }

    private static <T, E> void postJSON(final DMNetworkBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).post(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler).setTag(config.getRequestTag());
    }

    private static <T, E> void putJSON(final DMNetworkBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).put(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler).setTag(config.getRequestTag());
    }

    private static <T, E> void deleteJSON(final DMNetworkBaseRequestConfig<T, E> config, final StringEntity entity, final AsyncHttpResponseHandler handler) {
        requestHandler = getClient(config.getContext()).delete(config.getContext(), config.getUrl(), entity, APPLICATION_JSON, handler);
    }
}
