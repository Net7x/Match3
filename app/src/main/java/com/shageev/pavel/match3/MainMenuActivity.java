package com.shageev.pavel.match3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainMenuActivity extends Activity {
    private TextView scoreEasy, scoreMedium, scoreHard;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        prefs = getSharedPreferences("Match3", Context.MODE_PRIVATE);

        scoreMedium = findViewById(R.id.scoreMedium);
        scoreEasy = findViewById(R.id.scoreEasy);
        scoreHard = findViewById(R.id.scoreHard);

        scoreEasy.setText(String.valueOf(prefs.getLong("scoreEasy", 0)));
        scoreMedium.setText(String.valueOf(prefs.getLong("scoreMedium", 0)));
        scoreHard.setText(String.valueOf(prefs.getLong("scoreHard", 0)));
    }

    public void startClassicGame(View view){
        Intent intent = new Intent(this, ClassicGame.class);
        intent.putExtra("GameType", GameType.Medium);
        startActivityForResult(intent, 0);
    }

    public void startEasyGame(View view){
        Intent intent = new Intent(this, ClassicGame.class);
        intent.putExtra("GameType", GameType.Easy);
        startActivityForResult(intent, 0);
    }

    public void startHardGame(View view){
        Intent intent = new Intent(this, ClassicGame.class);
        intent.putExtra("GameType", GameType.Hard);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        scoreEasy.setText(String.valueOf(prefs.getLong("scoreEasy", 0)));
        scoreMedium.setText(String.valueOf(prefs.getLong("scoreMedium", 0)));
        scoreHard.setText(String.valueOf(prefs.getLong("scoreHard", 0)));
    }
}
