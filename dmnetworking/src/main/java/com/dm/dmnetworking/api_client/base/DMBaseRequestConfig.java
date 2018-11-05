package com.dm.dmnetworking.api_client.base;

import android.content.Context;

import com.dm.dmnetworking.api_client.constants.DMINetworkingConstants;
import com.dm.dmnetworking.parser.DMParserConfigs;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;


public final class DMBaseRequestConfig<T, E> {

    private Context context;

    private DMINetworkingConstants.Method method = DMINetworkingConstants.Method.GET;

    private String url;

    private Map<String, Object> params;

    private Object jsonObject;

    private DMParserConfigs<T> parserConfigs;

    private DMParserConfigs<E> errorParserConfigs;

    private String requestTag;

    private boolean isEnableDownload;

    private boolean isFullUrl;

    public DMBaseRequestConfig(final Context context) {
        this.context = context;
    }

    public DMBaseRequestConfig<T, E> setMethod(final DMINetworkingConstants.Method method) {
        this.method = method;
        return this;
    }

    public DMBaseRequestConfig<T, E> setUrl(final String url) {
        this.url = url;
        isFullUrl = false;
        return this;
    }

    public DMBaseRequestConfig<T, E> setFullUrl(final String fullUrl) {
        this.url = fullUrl;
        isFullUrl = true;
        return this;
    }

    public DMBaseRequestConfig<T, E> setParams(final Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public DMBaseRequestConfig<T, E> setParserConfigs(final DMParserConfigs<T> parserConfigs) {
        this.parserConfigs = parserConfigs;
        return this;
    }

    public DMBaseRequestConfig<T, E> setErrorParserConfigs(final DMParserConfigs<E> errorParserConfigs) {
        this.errorParserConfigs = errorParserConfigs;
        return this;
    }

    public DMBaseRequestConfig<T, E> setRequestTag(final String requestTag) {
        this.requestTag = requestTag;
        return this;
    }

    public DMBaseRequestConfig<T, E> setEnableDownload(final boolean enableDownload) {
        isEnableDownload = enableDownload;
        return this;
    }

    boolean isFullUrl() {
        return isFullUrl;
    }

    /**
     * @param json can be only JSONObject or JSONArray
     */
    public DMBaseRequestConfig<T, E> setJson(final Object json) {
        this.jsonObject = json;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public DMINetworkingConstants.Method getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public DMParserConfigs<T> getParserConfigs() {
        return parserConfigs;
    }

    public DMParserConfigs<E> getErrorParserConfigs() {
        return errorParserConfigs;
    }

    public String getRequestTag() {
        return requestTag;
    }

    public boolean isEnableDownload() {
        return isEnableDownload;
    }

    public boolean isJSONRequest() {
        if (getParams() != null) {
            return false;
        }

        return getJsonObject() != null;
    }

    public Object getJsonObject() {
        return jsonObject;
    }

    public String getJsonString() {
        if (jsonObject != null) {
            return jsonObject.toString();
        }
        return null;
    }

    public RequestParams getRequestParams() {
        final RequestParams requestParams = new RequestParams();

        if (params != null) {
            for (final Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof File) {
                    final File file = (File) entry.getValue();
                    try {
                        requestParams.put(entry.getKey(), file);
                    } catch (final FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    requestParams.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return requestParams;
    }
}
