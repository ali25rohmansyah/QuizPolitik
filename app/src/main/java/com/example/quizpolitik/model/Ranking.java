package com.example.quizpolitik.model;

public class Ranking {

    private long Score;
    private String Username;

    public Ranking() {
    }

    public long getScore() {
        return Score;
    }

    public void setScore(long score) {
        Score = score;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Ranking(long score, String username) {
        Score = score;
        Username = username;
    }
}