package com.dm.dmnetworking.api_client.listeners;

import org.json.JSONObject;

public interface DMIStatusHandleListener {

    void onComplete(String status, JSONObject jsonObject);

    default void onTokenUpdate() {

    }

    default void onError(String status, JSONObject jsonObject) {

    }
}
