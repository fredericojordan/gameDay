package com.fvj.gameday;

public class MatchInfo {

    public String team1 = "";
    public String team2 = "";
    public String time = "";
    public String score = "";
    public String league = "";

    public MatchInfo(String team1, String team2, String time, String score, String league) {
        this.team1 = team1;
        this.team2 = team2;
        this.time = time;
        this.score = score;
        this.league = league;

        System.out.println("/------------------");
        System.out.println(team1 + " " + score + " " + team2);
        System.out.println(time);
        System.out.println(league);
        System.out.println("------------------/");
    }
}
