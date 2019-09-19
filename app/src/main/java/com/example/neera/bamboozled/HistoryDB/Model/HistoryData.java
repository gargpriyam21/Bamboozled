package com.example.neera.bamboozled.HistoryDB.Model;

/**
 * Created by Neera on 07/10/17.
 */

public class HistoryData {
    int id;
    String score;
    String date;


    public HistoryData(int id, String score, String date) {
        this.id = id;
        this.score = score;
        this.date = date;

    }

    public HistoryData(String score, String date) {
        this.score = score;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public String getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }


}
