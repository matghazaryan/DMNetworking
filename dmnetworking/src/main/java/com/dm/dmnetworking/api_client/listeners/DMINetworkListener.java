package com.dm.dmnetworking.api_client.listeners;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public interface DMINetworkListener<T, E> extends DMINetworkErrorListener<E> {

    default void onComplete(final int statusCode, final JSONObject response) {

    }

    default void onComplete(final int statusCode, final String status, final JSONObject response) {

    }

    default void onComplete(final int statusCode, final String status, final T t) {

    }

    default void onComplete(final int statusCode, final String status, final List<T> tList) {

    }

    default void onComplete(final int statusCode, final File file) {

    }
}
