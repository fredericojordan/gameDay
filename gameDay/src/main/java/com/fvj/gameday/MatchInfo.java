package com.fvj.gameday;

import org.jsoup.select.Elements;

public class MatchInfo {

    public String time = "";
    public String team1 = "";
    public String team2 = "";

    public MatchInfo() {}

    public MatchInfo(Elements match_div) {
        this.team1 = match_div.select("a").select("div").eq(0).text();
        this.team2 = match_div.select("a").select("div").eq(1).text();
        this.time = match_div.select("a").select("div").size()>3?match_div.select("a").select("div").eq(3).text():
                match_div.select("a").select("div").eq(2).text();

        System.out.println("---");
        System.out.println(match_div.html());
        System.out.println("---");
    }
}
