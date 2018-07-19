package com.shageev.pavel.match3.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {HighScore.class, Resource.class}, version = 2, exportSchema = false)
public abstract class Db extends RoomDatabase {
    public abstract hsDao hsDao();

    private static Db INSTANCE;

    static Db getDb(final Context context){
        if(INSTANCE == null){
            synchronized (Db.class){
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        Db.class,
                        "hs_database")
                        .allowMainThreadQueries()
                        //.fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
