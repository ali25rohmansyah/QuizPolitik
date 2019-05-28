package com.example.quizpolitik.model;

public class QuestionScore {
    private String QuestionScore;
    private String User;
    private String Score;
    private String CategoryId;
    private String CategoryName;

    public QuestionScore() {
    }

    public String getQuestionScore() {
        return QuestionScore;
    }

    public void setQuestionScore(String questionScore) {
        QuestionScore = questionScore;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public QuestionScore(String questionScore, String user, String score, String categoryId, String categoryName) {
        QuestionScore = questionScore;
        User = user;
        Score = score;
        CategoryId = categoryId;
        CategoryName = categoryName;
    }
}
