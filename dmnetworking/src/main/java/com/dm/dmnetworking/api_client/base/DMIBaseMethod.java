package com.dm.dmnetworking.api_client.base;

import com.dm.dmnetworking.api_client.constants.DMINetworkingConstants;


public interface DMIBaseMethod extends DMINetworkingConstants {

    default String getTagForLogger() {
        return TAG;
    }

    default boolean isEnableLogger() {
        return IS_ENABLE_LOGGER;
    }

    default DMBaseTokenHandler onTokenRefresh() {
        return null;
    }
}
