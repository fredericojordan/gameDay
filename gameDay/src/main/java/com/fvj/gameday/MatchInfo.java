package com.fvj.gameday;

import org.jsoup.select.Elements;

public class MatchInfo {

    public String matchTime = "";
    public String team1 = "";
    public String team2 = "";
    public String score = "";

    public MatchInfo(Elements match_div) {
        System.out.println("team1=" + match_div.select("a").select("div").eq(0).text());
        System.out.println("team2=" + match_div.select("a").select("div").eq(1).text());
        System.out.println("time=" + match_div.select("a").select("div").eq(2).text());
        this.team1 = match_div.select("a").select("div").eq(0).text();
        this.team2 = match_div.select("a").select("div").eq(1).text();
        this.matchTime = match_div.select("a").select("div").size()>3?match_div.select("a").select("div").eq(3).text():
                match_div.select("a").select("div").eq(2).text();
    }
}
