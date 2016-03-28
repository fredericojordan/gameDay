package com.fvj.gameday;

public class MatchInfo {

    public String team1 = "";
    public String team2 = "";
    public String time = "";
    public String score = "";

    public MatchInfo(String team1, String team2, String time, String score) {
        this.team1 = team1;
        this.team2 = team2;
        this.time = time;
        this.score = score;

        System.out.println("/###");
        System.out.println(team1);
        System.out.println(team2);
        System.out.println(time);
        System.out.println(score);
        System.out.println("###/");
    }
}
