package com.dm.dmnetworking;


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
}
