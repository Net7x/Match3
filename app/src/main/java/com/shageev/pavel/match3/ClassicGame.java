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

        GridLayout ll = new GridLayout(this);
        //LinearLayout ll = new LinearLayout(this);
        //ll.setOrientation(LinearLayout.VERTICAL);

        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int layoutWidth = size.x;
        int layoutHeight = size.y;

        int tileWidth = layoutWidth / 8;

        ImageView imageView = new ImageView(this);

        imageView.setImageResource(R.drawable.a1);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(tileWidth,tileWidth));

        ll.addView(imageView);

        ImageView a2 = new ImageView(this);

        a2.setImageResource(R.drawable.a2);

        a2.setLayoutParams(new LinearLayout.LayoutParams(tileWidth, tileWidth));

        ll.addView(a2);

        setContentView(ll);

    }
}
