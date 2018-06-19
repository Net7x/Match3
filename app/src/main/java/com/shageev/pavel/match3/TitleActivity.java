package com.shageev.pavel.match3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Activity current = this;
        ResourceManager.getInstance().loadImages(current.getResources());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(current, MainMenuActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }
}
