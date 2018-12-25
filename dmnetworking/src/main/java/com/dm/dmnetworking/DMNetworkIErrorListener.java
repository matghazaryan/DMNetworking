package com.dm.dmnetworking;

import org.json.JSONObject;

interface DMNetworkIErrorListener<E> extends DMNetworkINoInternetConnectionListener {

    default void onError(final int statusCode, final String status, final JSONObject response) {
    }

    default void onError(final int statusCode, final String status, final E e) {
    }

    default void onError(final int statusCode, final JSONObject response) {
    }
}
