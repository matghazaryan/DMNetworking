package com.dm.dmnetworking.api_client.listeners;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public interface DMIClientListener {

    void onComplete(int statusCode, Header[] headers, JSONObject jsonObject);

    void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse);
}
