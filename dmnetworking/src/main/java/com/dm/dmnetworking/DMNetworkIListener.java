package com.dm.dmnetworking;

import com.dm.dmnetworking.model.progress.FileProgress;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public interface DMNetworkIListener<T, E> extends DMNetworkIErrorListener<E> {

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

    default void onFileProgress(final FileProgress fileProgress) {

    }
}
