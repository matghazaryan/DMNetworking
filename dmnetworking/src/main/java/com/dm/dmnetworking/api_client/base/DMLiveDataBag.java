package com.dm.dmnetworking.api_client.base;

import android.arch.lifecycle.LiveData;

import com.dm.dmnetworking.api_client.base.model.error.ErrorE;
import com.dm.dmnetworking.api_client.base.model.error.ErrorResponse;
import com.dm.dmnetworking.api_client.base.model.success.SuccessListT;
import com.dm.dmnetworking.api_client.base.model.success.SuccessResponse;
import com.dm.dmnetworking.api_client.base.model.success.SuccessT;

import org.json.JSONObject;


public final class DMLiveDataBag<T, E> {

    private LiveData<JSONObject> successJsonResponse;
    private LiveData<SuccessResponse> successResponse;
    private LiveData<SuccessT<T>> successT;
    private LiveData<SuccessListT<T>> successListT;

    private LiveData<ErrorE<E>> errorE;
    private LiveData<ErrorResponse> errorResponse;
    private LiveData<JSONObject> errorJsonResponse;
    private LiveData<String> noInternetConnection;


    void setSuccessJsonResponse(final LiveData<JSONObject> successJsonResponse) {
        this.successJsonResponse = successJsonResponse;
    }

    void setErrorJsonResponse(final LiveData<JSONObject> errorJsonResponse) {
        this.errorJsonResponse = errorJsonResponse;
    }

    void setSuccessResponse(final LiveData<SuccessResponse> successResponse) {
        this.successResponse = successResponse;
    }

    void setSuccessT(final LiveData<SuccessT<T>> successT) {
        this.successT = successT;
    }

    void setSuccessListT(final LiveData<SuccessListT<T>> successListT) {
        this.successListT = successListT;
    }

    void setErrorE(final LiveData<ErrorE<E>> errorE) {
        this.errorE = errorE;
    }

    void setErrorResponse(final LiveData<ErrorResponse> errorResponse) {
        this.errorResponse = errorResponse;
    }

    void setNoInternetConnection(final LiveData<String> noInternetConnection) {
        this.noInternetConnection = noInternetConnection;
    }

    public LiveData<JSONObject> getSuccessJsonResponse() {
        return successJsonResponse;
    }

    public LiveData<JSONObject> getErrorJsonResponse() {
        return errorJsonResponse;
    }

    public LiveData<SuccessResponse> getSuccessResponse() {
        return successResponse;
    }

    public LiveData<SuccessT<T>> getSuccessT() {
        return successT;
    }

    public LiveData<SuccessListT<T>> getSuccessListT() {
        return successListT;
    }

    public LiveData<ErrorE<E>> getErrorE() {
        return errorE;
    }

    public LiveData<ErrorResponse> getErrorResponse() {
        return errorResponse;
    }

    public LiveData<String> getNoInternetConnection() {
        return noInternetConnection;
    }
}
