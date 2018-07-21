package com.shageev.pavel.matchballs.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "high_scores")
public class HighScore {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int gameType;

    public int year;
    public int month;
    public int day;

    public long score;
}
