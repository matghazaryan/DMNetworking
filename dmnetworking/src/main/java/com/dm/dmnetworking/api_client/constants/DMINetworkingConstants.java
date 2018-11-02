package com.dm.dmnetworking.api_client.constants;

public interface DMINetworkingConstants {

    String APPLICATION_JSON = "application/json";
    String UTF_8 = "UTF-8";

    String TAG = "API";
    boolean IS_ENABLE_LOGGER = true;
    String URL = "URL:";
    String STATUS_CODE = "statusCode";
    String ERROR = "error";
    String RESPONSE_STRING = "responseString";
    String NO_INTERNET_CONNECTION = "no_internet_connection";

    enum Method {
        POST, GET, PUT, DELETE
    }

    enum ParseObject {
        JSON_OBJECT, JSON_ARRAY, OTHER
    }
}
