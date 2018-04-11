package com.staser.testculinaryapplication.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.staser.testculinaryapplication.common.Common.LOG;


public class TaskGetHttp extends AsyncTask<Request, Integer, String> {

    private String url;

    private OkHttpClient okHttpClient;
    private Request request;

    public TaskGetHttp(String url) {
        super();
        this.url = url;

        Log.d(LOG, "... ... new TaskGetHttp was created");
        Log.i(LOG, "... url ... is: " + url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.okHttpClient = new OkHttpClient();
        this.request = new Request.Builder()
                .url(this.url)
                .build();

        Log.d(LOG, getClass().getName() + " ... ... onPreExecute");
        Log.i(LOG, getClass().getName() + " ... request ... is: " + this.request);
    }

    @Override
    protected String doInBackground(Request... requests) {
        String _responseStr = null;
        try {
            Response _response = this.okHttpClient.newCall(this.request).execute();
            if (_response.message().equals("OK")) {
                _responseStr = this.doStringFromResponse(_response);

            }else {
                Log.d(LOG, getClass().getName() + " ... doInBackground ... response get that msg.: " + _response.message());
                _responseStr = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG, getClass().getName() + " ... ... doInBackground");
        return _responseStr;
    }

    private String doStringFromResponse(Response response) {
        String _responseStr = null;
        try {
            _responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(LOG, getClass().getName() + "... response is ...: " + _responseStr);
        return _responseStr;
    }
}
