package com.dm.dmnetworking;

import com.dm.dmnetworking.model.progress.FileProgress;

import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;


interface DMNetworkIClientListener {

    void onComplete(int statusCode, Header[] headers, JSONObject jsonObject, File file);

    void onFileProgress(FileProgress progress);

    void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse);
}
