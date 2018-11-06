package com.dm.dmnetworking.api_client.listeners;

import com.dm.dmnetworking.api_client.base.model.progress.FileProgress;

import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;


public interface DMIClientListener {

    void onComplete(int statusCode, Header[] headers, JSONObject jsonObject, File file);

    void onFileProgress(FileProgress progress);

    void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse);
}
