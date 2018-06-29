package com.shageev.pavel.match3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ClassicGame extends Activity {
    private GameView gv;
    private GameType gType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        gType = (GameType)intent.getSerializableExtra("GameType");
        //gv = new GameView(this);
        //setContentView(gv);
        ResourceManager.getInstance().initPrefs(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ResourceManager rm = ResourceManager.getInstance();
        rm.loadImages(this.getResources());
        rm.scaleImages();
        if(gv == null){
            gv = new GameView(this, gType);
            setContentView(gv);
        }
    }
}
