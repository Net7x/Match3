package com.shageev.pavel.match3;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ClassicGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        ResourceManager.getInstance().loadImages(this.getResources());
        ResourceManager.getInstance().scaleImages();
    }
}
