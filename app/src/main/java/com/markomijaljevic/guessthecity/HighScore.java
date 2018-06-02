package com.markomijaljevic.guessthecity;

/**
 * Created by Marko on 13.9.2017..
 */

public class HighScore {

    private String UserName;
    private int Score;

    public HighScore(){

    }

    public  HighScore(String username,int score){

        this.Score = score;
        this.UserName = username;

    }

    public String getUserName(){

        return UserName;
    }


    public int getScore(){

        return Score;
    }

}
