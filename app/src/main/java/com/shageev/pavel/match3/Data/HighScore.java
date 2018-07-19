package com.shageev.pavel.match3.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.shageev.pavel.match3.GameType;

import java.util.Date;

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
