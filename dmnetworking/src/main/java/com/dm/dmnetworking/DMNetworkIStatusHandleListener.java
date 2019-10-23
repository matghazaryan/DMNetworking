package com.dm.dmnetworking;

import org.json.JSONObject;

public interface DMNetworkIStatusHandleListener {

    void onComplete(String status, JSONObject jsonObject);

    default void onTokenUpdate() {}

    default void onError(String status, JSONObject jsonObject) {

    }
}
