package com.example.finalproject;

public class Score {

    private int currentScore = 0;

    public void increaseScore() {
        this.currentScore++;
    }

    public int getCurrentScore() {
        return this.currentScore;
    }
}
