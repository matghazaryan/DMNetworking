package com.dm.dmnetworking.api_client.listeners;

import org.json.JSONObject;

public interface DMINetworkErrorListener<E> extends DMINoInternetConnectionListener {

    default void onError(final String status, final JSONObject response) {
    }

    default void onError(final String status, final E e) {
    }

    default void onError(final JSONObject response) {
    }
}
