package com.dm.dmnetworking;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;


public final class DMNetworkBaseRequestConfig<T, E> {

    private Context context;

    private DMNetworkIConstants.Method method = DMNetworkIConstants.Method.GET;

    private String url;

    private Map<String, Object> params;

    private Object jsonObject;

    private DMNetworkParserConfigs<T> parserConfigs;

    private DMNetworkParserConfigs<E> errorParserConfigs;

    private String requestTag;

    private boolean isFullUrl;

    private DMNetworkIConstants.HttpHandlerType httpHandlerType;

    public DMNetworkBaseRequestConfig(final Context context) {
        this.context = context;
    }

    public DMNetworkBaseRequestConfig<T, E> setMethod(final DMNetworkIConstants.Method method) {
        this.method = method;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setUrl(final String url) {
        this.url = url;
        isFullUrl = false;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setFullUrl(final String fullUrl) {
        this.url = fullUrl;
        isFullUrl = true;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setParams(final Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setParserConfigs(final DMNetworkParserConfigs<T> parserConfigs) {
        this.parserConfigs = parserConfigs;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setErrorParserConfigs(final DMNetworkParserConfigs<E> errorParserConfigs) {
        this.errorParserConfigs = errorParserConfigs;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setRequestTag(final String requestTag) {
        this.requestTag = requestTag;
        return this;
    }

    public DMNetworkBaseRequestConfig<T, E> setHttpHandlerType(final DMNetworkIConstants.HttpHandlerType httpHandlerType) {
        this.httpHandlerType = httpHandlerType;
        return this;
    }

    boolean isFullUrl() {
        return isFullUrl;
    }

    /**
     * @param json can be only JSONObject or JSONArray
     */
    public DMNetworkBaseRequestConfig<T, E> setJson(final Object json) {
        this.jsonObject = json;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public DMNetworkIConstants.Method getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public DMNetworkParserConfigs<T> getParserConfigs() {
        return parserConfigs;
    }

    public DMNetworkParserConfigs<E> getErrorParserConfigs() {
        return errorParserConfigs;
    }

    public String getRequestTag() {
        return requestTag;
    }

    public DMNetworkIConstants.HttpHandlerType getHttpHandlerType() {
        return httpHandlerType;
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
