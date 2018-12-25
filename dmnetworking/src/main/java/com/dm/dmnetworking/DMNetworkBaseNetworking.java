package com.dm.dmnetworking;

import com.dm.dmnetworking.model.progress.FileProgress;

import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;


abstract class DMNetworkBaseNetworking extends DMNetworkBaseHelper {

    final <T, E> void doRequest(final DMNetworkBaseRequestConfig<T, E> config, final DMNetworkIListener<T, E> listener) {

        beforeRequest(config.getContext(), () -> {
            if (!config.isFullUrl()) {
                config.setUrl(getFullUrl(config.getContext(), config.getUrl()));
            }

            DMNetworkAPIClient.makeRequest(config, new DMNetworkIClientListener() {
                @Override
                public void onComplete(final int statusCode, final Header[] headers, final JSONObject jsonObject, final File file) {

                    showLogs(config.getUrl(), jsonObject, file);

                    handleStatuses(config.getContext(), statusCode, jsonObject, new DMNetworkIStatusHandleListener() {
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
                public void onFileProgress(final FileProgress progress) {
                    listener.onFileProgress(progress);
                }

                @Override
                public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                    DMNetworkBaseNetworking.this.onFailure(config, statusCode, throwable, errorResponse, listener);
                }
            });
        });
    }
}
