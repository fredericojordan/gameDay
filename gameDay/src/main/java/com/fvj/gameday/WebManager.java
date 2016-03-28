package com.fvj.gameday;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WebManager extends AsyncTask<String, Void, ArrayList<MatchInfo>> {
    private static final String TAG = "WebManager";
    public static final String URL = "http://wiki.teamliquid.net/counterstrike/Liquipedia:Upcoming_and_ongoing_matches";

    public static int MAX_UPCOMING_GAMES = 10;

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
        System.out.println("Printing...");
        ArrayList<MatchInfo> matches = new ArrayList<MatchInfo>();

        Element ongoing_div = doc.select("div[id=infobox_matches]").get(0);
        Elements ongoing_games = ongoing_div.select("table[class=wikitable infobox_matches_content]");
        System.out.println(ongoing_games.size() + " ongoing games.");

        for (int i=0; i<ongoing_games.size(); i++) {
            Elements match_div = ongoing_games.eq(i);
            String team1 = match_div.select("td[class=team-left]").text();
            String team2 = match_div.select("td[class=team-right]").text();
            String time = "LIVE";
            String score = match_div.select("td[class=versus]").text();
            matches.add(new MatchInfo(team1, team2, time, score));
        }

        Element upcoming_div = doc.select("div[id=infobox_matches]").get(1);
        Elements upcoming_games = upcoming_div.select("table[class=wikitable infobox_matches_content]");
        System.out.println(upcoming_games.size() + " upcoming games. Showing first " + MAX_UPCOMING_GAMES + " entries...");

        for (int i=0; i<upcoming_games.size(); i++) {
            if (i>MAX_UPCOMING_GAMES) break;
            Elements match_div = upcoming_games.eq(i);
            String team1 = match_div.select("td[class=team-left]").text();
            String team2 = match_div.select("td[class=team-right]").text();
            String time = match_div.select("span[class=datetime]").text();
            String score = "vs";
            matches.add(new MatchInfo(team1, team2, time, score));
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