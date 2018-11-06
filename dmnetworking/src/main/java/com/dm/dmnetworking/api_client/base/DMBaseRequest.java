package com.dm.dmnetworking.api_client.base;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.dm.dmnetworking.api_client.base.model.error.ErrorE;
import com.dm.dmnetworking.api_client.base.model.error.ErrorResponse;
import com.dm.dmnetworking.api_client.base.model.progress.FileProgress;
import com.dm.dmnetworking.api_client.base.model.success.SuccessListT;
import com.dm.dmnetworking.api_client.base.model.success.SuccessResponse;
import com.dm.dmnetworking.api_client.base.model.success.SuccessT;
import com.dm.dmnetworking.api_client.listeners.DMINetworkListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public abstract class DMBaseRequest extends DMBaseNetworking {

    public final <T, E> void request(final DMBaseRequestConfig<T, E> config, final DMINetworkListener<T, E> listener) {

        boolean isRequestDataSuccess = true;
        try {
            if (config == null) {
                throw new Exception("BaseRequestConfig: Config cannot be null");
            } else if (config.getContext() == null) {
                throw new Exception("BaseRequestConfig: Context cannot be null");
            } else if (config.getUrl() == null) {
                throw new Exception("BaseRequestConfig: Url or fullUrl cannot be null in same time");
            } else if (listener == null) {
                throw new Exception("INetworkListener: DMINetworkListener cannot be null");
            } else if (config.getParams() == null && config.getJsonObject() != null) {
                if (!(config.getJsonObject() instanceof JSONObject) && !(config.getJsonObject() instanceof JSONArray)) {
                    throw new Exception("BaseRequestConfig: Object must be JSONObject or JSONArray, now it is " + config.getJsonObject().getClass());
                }
            }

            if (config.getParams() == null && config.getJsonObject() == null) {
                config.setParams(new HashMap<>());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            isRequestDataSuccess = false;
        }


        if (isRequestDataSuccess && isNeedToMakeRequest(config.getContext(), listener)) {
            DMBaseAPIClient.setRequestTimeOut(getRequestTimeOut());
            DMBaseAPIClient.setTag(getTagForLogger());
            DMBaseAPIClient.setEnableLogger(isEnableLogger());


            doRequest(config, listener);
        }
    }

    @Override
    final void requestForToken(final int statusCode, final Context context, final DMBaseTokenHandler baseToken, final DMIBaseOnTokenRefreshListener listener) {
        if (baseToken != null) {
            final DMBaseRequestConfig<Object, Object> config = new DMBaseRequestConfig<>(context)
                    .setUrl(baseToken.getRefreshTokenUrl());

            doRequest(config, new DMINetworkListener<Object, Object>() {
                @Override
                public void onComplete(final int statusCode, final JSONObject response) {
                    baseToken.onTokenRefreshed(context, response);
                    listener.onRefresh();
                }

                @Override
                public void onError(final int statusCode, final JSONObject response) {
                    baseToken.onTokenRefreshFailure(context, response);
                }

                @Override
                public void onNoInternetConnection() {
                    baseToken.onNoInternetConnection(context);
                }
            });
        }
    }

    public final <T, E> DMLiveDataBag<T, E> request(final DMBaseRequestConfig<T, E> config) {

        final DMLiveDataBag<T, E> liveDataBag = new DMLiveDataBag<>();

        final MutableLiveData<JSONObject> responseMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<SuccessResponse> dmResponseMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<SuccessT<T>> dmtMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<SuccessListT<T>> dmListTMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<File> dmFileMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<FileProgress> fileProgressMutableLiveData = new MutableLiveData<>();


        final MutableLiveData<ErrorE<E>> dmErrorEMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<ErrorResponse> dmErrorResponseMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<JSONObject> errorResponseMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<String> noInternetConnection = new MutableLiveData<>();


        liveDataBag.setSuccessJsonResponse(responseMutableLiveData);
        liveDataBag.setSuccessResponse(dmResponseMutableLiveData);
        liveDataBag.setSuccessT(dmtMutableLiveData);
        liveDataBag.setSuccessListT(dmListTMutableLiveData);
        liveDataBag.setSuccessFile(dmFileMutableLiveData);
        liveDataBag.setFileProgress(fileProgressMutableLiveData);


        liveDataBag.setErrorE(dmErrorEMutableLiveData);
        liveDataBag.setErrorJsonResponse(errorResponseMutableLiveData);
        liveDataBag.setErrorResponse(dmErrorResponseMutableLiveData);
        liveDataBag.setNoInternetConnection(noInternetConnection);


        boolean isRequestDataSuccess = true;
        try {
            if (config == null) {
                throw new Exception("BaseRequestConfig: Config cannot be null");
            } else if (config.getContext() == null) {
                throw new Exception("BaseRequestConfig: Context cannot be null");
            } else if (config.getUrl() == null) {
                throw new Exception("BaseRequestConfig: Url or fullUrl cannot be null in same time");
            } else if (config.getParams() == null && config.getJsonObject() != null) {
                if (!(config.getJsonObject() instanceof JSONObject) && !(config.getJsonObject() instanceof JSONArray)) {
                    throw new Exception("BaseRequestConfig: Object must be JSONObject or JSONArray, now it is " + config.getJsonObject().getClass());
                }
            }

            if (config.getParams() == null && config.getJsonObject() == null) {
                config.setParams(new HashMap<>());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            isRequestDataSuccess = false;
        }


        if (isRequestDataSuccess && isNeedToMakeRequest(config.getContext(), new DMINetworkListener() {
            @Override
            public void onNoInternetConnection() {
                noInternetConnection.setValue(NO_INTERNET_CONNECTION);
            }
        })) {
            DMBaseAPIClient.setRequestTimeOut(getRequestTimeOut());
            DMBaseAPIClient.setTag(getTagForLogger());
            DMBaseAPIClient.setEnableLogger(isEnableLogger());

            final DMINetworkListener<T, E> listener = new DMINetworkListener<T, E>() {
                @Override
                public void onComplete(final int statusCode, final JSONObject response) {
                    responseMutableLiveData.setValue(response);
                }

                @Override
                public void onComplete(final int statusCode, final String status, final JSONObject response) {
                    dmResponseMutableLiveData.setValue(new SuccessResponse(statusCode, status, response));
                }

                @Override
                public void onComplete(final int statusCode, final String status, final T t) {
                    dmtMutableLiveData.setValue(new SuccessT<>(statusCode, status, t));
                }

                @Override
                public void onComplete(final int statusCode, final String status, final List<T> tList) {
                    dmListTMutableLiveData.setValue(new SuccessListT<>(statusCode, status, tList));
                }

                @Override
                public void onComplete(final int statusCode, final File file) {
                    dmFileMutableLiveData.setValue(file);
                }

                @Override
                public void onFileProgress(final FileProgress fileProgress) {
                    fileProgressMutableLiveData.setValue(fileProgress);
                }

                @Override
                public void onError(final int statusCode, final String status, final JSONObject response) {
                    dmErrorResponseMutableLiveData.setValue(new ErrorResponse(statusCode, status, response));
                }

                @Override
                public void onError(final int statusCode, final String status, final E e) {
                    dmErrorEMutableLiveData.setValue(new ErrorE<>(statusCode, status, e));
                }

                @Override
                public void onError(final int statusCode, final JSONObject response) {
                    errorResponseMutableLiveData.setValue(response);
                }

                @Override
                public void onNoInternetConnection() {
                    noInternetConnection.setValue(NO_INTERNET_CONNECTION);
                }
            };


            doRequest(config, listener);
        }

        return liveDataBag;
    }
}
