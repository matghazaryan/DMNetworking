package com.dm.dmnetworking;

import com.dm.dmnetworking.model.progress.FileProgress;

import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;


abstract class DMNetworkBaseNetworking extends DMNetworkBaseHelper {

    final <T, E> void doRequest(final DMNetworkBaseRequestConfig<T, E> config, final DMNetworkIListener<T, E> listener) {

        final String url = config.getUrl();

        beforeRequest(config.getContext(), url, () -> {

            if (!config.isFullUrl()) {
                config.setFullUrl(getFullUrl(config.getContext(), config.getUrl()));
            }

            tryToLoadJSONFromAsset(config.getContext(), getFakeJsonFilePath(url), fakeJsonObject -> {

                if (isEnableFakeJson() && fakeJsonObject != null) {
                    handleResponse(config, url, FAKE_FILE, FAKE_STATUS_CODE, fakeJsonObject, JsonType.FAKE_JSON, listener);
                    return;
                }

                DMNetworkAPIClient.makeRequest(config, new DMNetworkIClientListener() {
                    @Override
                    public void onComplete(final int statusCode, final Header[] headers, final JSONObject jsonObject, final File file) {
                        handleResponse(config, url, file, statusCode, jsonObject, JsonType.REAL_JSON, listener);
                    }

                    @Override
                    public void onFileProgress(final FileProgress progress) {
                        listener.onFileProgress(progress);
                    }

                    @Override
                    public void onFailure(final int statusCode, final Header[] headers, final Throwable throwable, final JSONObject errorResponse) {
                        onSuccessOrFailureResponseForDebug(url, errorResponse, ResponseType.FAILURE);
                        DMNetworkBaseNetworking.this.onFailure(config, statusCode, throwable, errorResponse, listener);
                    }
                });
            });
        });
    }

    private <T, E> void handleResponse(final DMNetworkBaseRequestConfig<T, E> config, final String url, final File file, final int statusCode, final JSONObject jsonObject, final JsonType jsonType, final DMNetworkIListener<T, E> listener) {

        onSuccessOrFailureResponseForDebug(url, jsonObject, ResponseType.SUCCESS);

        showLogs(config.getUrl(), jsonObject, file, jsonType);

        handleStatuses(config.getContext(), statusCode, config.getUrl(), jsonObject, new DMNetworkIStatusHandleListener() {
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
}
