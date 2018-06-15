package com.shageev.pavel.match3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    GameLoop gameLoop;
    private SurfaceHolder holder;
    int sw,sh;
    Bitmap a1,a2,a3, a1Scaled;

    public GameView(Context context){
        super(context);
        gameLoop = new GameLoop(this);
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoop.SetRunning(true);
                gameLoop.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
                sw = w;
                sh = h;
                a1Scaled = Bitmap.createScaledBitmap(a1, sw/8, sw/8, true);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                gameLoop.SetRunning(false);
                gameLoop.getThreadGroup().interrupt();
            }
        });

        Display display = getDisplay();
        sw = getWidth();
        sh = getHeight();
        a1 = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
        a2 = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
        a3 = BitmapFactory.decodeResource(getResources(), R.drawable.a3);

    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawARGB(1,0,0,50);
        canvas.drawBitmap(a1Scaled, sw / 2, sh /2, null);
    }
}
