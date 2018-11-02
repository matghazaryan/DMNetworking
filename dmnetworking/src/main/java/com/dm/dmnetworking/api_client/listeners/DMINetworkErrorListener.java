package com.dm.dmnetworking.api_client.listeners;

import org.json.JSONObject;

public interface DMINetworkErrorListener<E> extends DMINoInternetConnectionListener {

    default void onError(final int statusCode, final String status, final JSONObject response) {
    }

    default void onError(final int statusCode, final String status, final E e) {
    }

    default void onError(final int statusCode, final JSONObject response) {
    }
}
