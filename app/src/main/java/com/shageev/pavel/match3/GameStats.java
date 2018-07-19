package com.shageev.pavel.match3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.shageev.pavel.match3.Data.hsDao;

public class GameStats extends Activity {
    private GameType gType;
    private TextView statsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_stats);
        Intent intent = getIntent();
        gType = (GameType)intent.getSerializableExtra("com.shageev.pavel.match3.GameType");

        statsTitle = findViewById(R.id.statsTitle);

        switch(gType){
            case Easy:
                statsTitle.setText(getString(R.string.easy_label) + " " + getString(R.string.stats_title));
                break;
            case Medium:
                statsTitle.setText(getString(R.string.expert_label) + " " + getString(R.string.stats_title));
                break;
            case Hard:
                statsTitle.setText(getString(R.string.master_label) + " " + getString(R.string.stats_title));
                break;
        }

    }

    private static class showDayStats extends AsyncTask<StatisticParams,Void,Void>{

        @Override
        protected Void doInBackground(StatisticParams... params) {
            return null;
        }
    }
    private class StatisticParams {
        GameType gameType;
        hsDao dao;

    }
}


