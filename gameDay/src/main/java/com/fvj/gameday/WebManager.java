package com.fvj.gameday;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WebManager extends AsyncTask<String, Void, ArrayList<MatchInfo>> {
    private static final String TAG = "WebManager";
    public static final String URL = "http://www.hltv.org/matches/";

    private Context mContext = null;
    public AsyncResponse delegate = null;

    public WebManager(Context context) { this.mContext = context; }

    protected ArrayList<MatchInfo> doInBackground(String... urls) { return getMatches(URL); }

    protected ArrayList<MatchInfo> getMatches(String url) {
        System.out.println("Starting web request!");

        try {
            Document doc = Jsoup.connect(url).get();
            return parseMatchesPage(doc);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    protected ArrayList<MatchInfo> parseMatchesPage(Document doc) {
        System.out.println("Parsing...");

//        <div class="panel panel-default col-xs-12">
        Elements match_divs = doc.select("div[class^=panel panel-default col-xs-12]");

        System.out.println("Size: " + match_divs.size());

        ArrayList<MatchInfo> matches = new ArrayList<MatchInfo>();
        for (int i=0; i<match_divs.size(); i++) {
            matches.add(new MatchInfo(match_divs.eq(i)));
        }
        return matches;
    }

    protected void onPostExecute(ArrayList<MatchInfo> matches) {

        if ( matches != null ) {
            System.out.printf("Returning %d matches...%n", matches.size());
            delegate.processFinish(mContext, matches);
        } else {
            System.out.println("Could not gather matches info!");
        }
    }
}