package com.fvj.gameday;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebManager extends AsyncTask<String, Void, String> {
    private static final String TAG = "WebManager";

    JSONObject page;

    protected String doInBackground(String... urls) {
        try {
            Document doc = Jsoup.connect(urls[0]).get();
            return doc.html();
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String str) {
        try {
            this.page = new JSONObject(str);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}