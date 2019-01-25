package com.dm.dmnetworking;


import org.json.JSONObject;

interface DMNetworkIBaseMethod extends DMNetworkIConstants {

    default String getTagForLogger() {
        return TAG;
    }

    default boolean isEnableLogger() {
        return IS_ENABLE_LOGGER;
    }

    default DMNetworkBaseTokenHandler onTokenRefresh() {
        return null;
    }

    default void onSuccessOrFailureResponseForDebug(final String url, final JSONObject jsonObject, final ResponseType responseType) {
    }

    /**
     * For use fake json in offline mode return true for function isNeedToMakeRequest
     */
    default String getFakeJsonFilePath(final String url) {
        return null;
    }
}
