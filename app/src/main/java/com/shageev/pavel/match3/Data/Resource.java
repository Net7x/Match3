package com.shageev.pavel.match3.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "resources")
public class Resource {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int year;
    public int month;
    public int day;

    public int gameType;

    public int ballType;

    public int qty;
}
