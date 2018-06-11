package com.shageev.pavel.match3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    public void startClassicGame(View view){
        Intent intent = new Intent(this, ClassicGame.class);
        startActivity(intent);
    }
}
