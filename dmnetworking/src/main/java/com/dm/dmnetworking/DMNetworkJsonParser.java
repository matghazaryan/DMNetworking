package com.dm.dmnetworking;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public final class DMNetworkJsonParser implements DMNetworkIConstants {

    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private static <T> T parseJsonObject(final JSONObject pResponse, final Class<T> aClass) throws IOException {
        return getObjectMapper().readValue(pResponse.toString(), aClass);
    }

    private static <T> List<T> parseJsonArray(final JSONArray pResponse, final Class<?> aClass) throws IOException {
        return getObjectMapper().readValue(pResponse.toString(), getObjectMapper().getTypeFactory().constructCollectionType(List.class, aClass));
    }

    public static <T> T parseObject(final JSONObject response, final Class<T> aClass, final String... jsonKeys) {
        try {
            JSONObject tempJson = response;
            for (final String s : jsonKeys) {
                tempJson = tempJson.optJSONObject(s);
                if (tempJson == null) {
                    final JSONArray jsonArray = response.optJSONArray(s);
                    if (jsonArray == null || jsonArray.length() == 0) {
                        return null;
                    }
                    tempJson = jsonArray.getJSONObject(0);
                }
            }
            return parseJsonObject(tempJson, aClass);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseArray(final JSONObject response, final Class<T> aClass, final String... jsonKeys) {
        try {
            JSONObject tempJson = response;
            for (int i = 0; i < jsonKeys.length - 1; i++) {
                final String s = jsonKeys[i];
                tempJson = tempJson.optJSONObject(s);
                if (tempJson == null) {
                    final JSONArray jsonArray = response.optJSONArray(s);
                    if (jsonArray == null || jsonArray.length() == 0) {
                        return null;
                    }
                    tempJson = jsonArray.getJSONObject(0);
                }
            }
            final JSONArray jsonArray = tempJson.getJSONArray(jsonKeys[jsonKeys.length - 1]);
            return parseJsonArray(jsonArray, aClass);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ParseObject getType(final JSONObject response, final String... jsonKeys) {

        JSONObject tempJson = response;
        JSONObject tempJsonOld;

        if (jsonKeys.length > 1) {
            for (int i = 0; i < jsonKeys.length; i++) {
                final String s = jsonKeys[i];
                tempJsonOld = tempJson;

                tempJson = tempJson.optJSONObject(s);

                if (i == (jsonKeys.length - 1)) {
                    if (tempJson != null) {
                        return ParseObject.JSON_OBJECT;
                    } else {
                        tempJson = tempJsonOld;
                        if (tempJson.optJSONArray(s) != null) {
                            return ParseObject.JSON_ARRAY;
                        }
                    }
                } else if (tempJson == null) {
                    return ParseObject.OTHER;
                }
            }
        } else if (jsonKeys.length == 1) {
            final String s = jsonKeys[0];
            tempJsonOld = tempJson;
            tempJson = tempJson.optJSONObject(s);

            if (tempJson != null) {
                return ParseObject.JSON_OBJECT;
            } else {
                tempJson = tempJsonOld;
                if (tempJson.optJSONArray(s) != null) {
                    return ParseObject.JSON_ARRAY;
                }
            }
        } else {
            return ParseObject.JSON_OBJECT;
        }

        return ParseObject.OTHER;
    }
}
