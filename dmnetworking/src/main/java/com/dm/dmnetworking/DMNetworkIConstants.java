package com.dm.dmnetworking;

import java.io.File;

public interface DMNetworkIConstants {

    String APPLICATION_JSON = "application/json";
    String UTF_8 = "UTF-8";

    String TAG = "API";
    boolean IS_ENABLE_LOGGER = true;
    String URL = "URL:";
    String STATUS_CODE = "statusCode";
    String ERROR = "error";
    String RESPONSE_STRING = "responseString";
    String NO_INTERNET_CONNECTION = "no_internet_connection";
    int FAKE_STATUS_CODE = -1000;
    File FAKE_FILE = null;

    enum Method {
        POST, GET, PUT, DELETE
    }

    enum ParseObject {
        JSON_OBJECT, JSON_ARRAY, OTHER
    }

    enum JsonType {
        REAL_JSON, FAKE_JSON
    }

    enum ResponseType {
        SUCCESS, FAILURE
    }
}
