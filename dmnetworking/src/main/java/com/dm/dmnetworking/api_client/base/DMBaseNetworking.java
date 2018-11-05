package com.dm.dmnetworking.api_client.base;

import com.dm.dmnetworking.api_client.client.DMAPIClient;
import com.dm.dmnetworking.api_client.listeners.DMIClientListener;
import com.dm.dmnetworking.api_client.listeners.DMINetworkListener;
import com.dm.dmnetworking.api_client.listeners.DMIStatusHandleListener;

import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;


abstract class DMBaseNetworking extends DMBaseHelper {

    final <T, E> void doRequest(final DMBaseRequestConfig<T, E> config, final DMINetworkListener<T, E> listener) {

        if (!config.isFullUrl()) {
            config.setUrl(getFullUrl(config.getUrl()));
        }

        DMAPIClient.makeRequest(config, new DMIClientListener() {
            @Override
            public void onComplete(final int statusCode, final Header[] headers, final JSONObject jsonObject, final File file) {

                showLogs(config.getUrl(), jsonObject, file);

                handleStatuses(config.getContext(), statusCode, jsonObject, new DMIStatusHandleListener() {
                    @Override
                    public void onComplete(final String status, final JSONObject jsonObject) {
                        if (file == null && jsonObject != null) {
                            onParseComplete(statusCode, status, jsonObject, config.getParserConfigs(), listener);
                        } else if (file != null) {
                            listener.onComplete(statusCode, file);
                        }
                    }

                    @Override
                    public void onTokenUpdate() {
                        requestForToken(statusCode, config.getContext(), onTokenRefresh(), () -> doRequest(config, listener));
                    }

                    @Override
                    public void onError(final String status, final JSONObject jsonObject) {
                        onErrorParse(statusCode, status, jsonObject, config.getErrorParserConfigs(), listener);
                    }
                });
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                DMBaseNetworking.this.onFailure(config, statusCode, throwable, errorResponse, listener);
            }
        });
    }
}
