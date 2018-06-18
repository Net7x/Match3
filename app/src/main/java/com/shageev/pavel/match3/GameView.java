package com.shageev.pavel.match3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView {
    GameLoop gameLoop;
    private SurfaceHolder holder;
    int sw,sh, screenDw, screenDh;
    int tileWidth;
    Bitmap a1,a2,a3,a4,a5,a6,a7,a8,a9;
    Bitmap a1Scaled, a2Scaled, a3Scaled, a4Scaled, a5Scaled, a6Scaled, a7Scaled, a8Scaled, a9Scaled;
    private GameView thisView;
    private int COLUMNS = 7;
    Random r = new Random();
    int[][] gameField;
    GameField gField;
    int moveTileCol, moveTileRow;
    int prevX, prevY;
    int deltaX, deltaY;
    ArrayList<Tile> Tiles;
    int SelectedTileIndex;

    public GameView(Context context){
        super(context);
        thisView = this;
        //gameLoop = new GameLoop(this);
        holder = getHolder();

        gameField = new int[][]{
                { 1, 2, 1, 0, 1, 1, 0, 2 },
                { 2, 1, 2, 1, 2, 0, 1, 2 },
                { 8, 2, 1, 0, 2, 3, 4, 1 },
                { 1, 3, 4, 6, 4, 1, 2, 1 },
                { 0, 8, 3, 2, 0, 1, 7, 3 },
                { 1, 2, 1, 0, 6, 1, 0, 7 },
                { 2, 3, 5, 1, 2, 0, 1, 2 },
                { 8, 2, 1, 0, 2, 3, 4, 1 }
        };

        Tiles = new ArrayList<Tile>();
        for(int i = 0; i < COLUMNS; i++){
            for(int j = 0; j < COLUMNS; j++){
                Tile t = new Tile();
                t.Column = j;
                t.Row = i;
                t.Type = gameField[i][j];
                t.dX = 0;
                t.dY = 0;
                Tiles.add(t);
            }
        }
        
        gField = new GameField(COLUMNS);
        gField.init(Tiles);

        SelectedTileIndex = -1;

        moveTileCol = COLUMNS;
        moveTileRow = COLUMNS;

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoop = new GameLoop(thisView);
                gameLoop.SetRunning(true);
                gameLoop.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
                setScreenDimensions(w, h);

                if(tileWidth > 0){
                    a1Scaled = Bitmap.createScaledBitmap(a1, tileWidth, tileWidth, true);
                    a2Scaled = Bitmap.createScaledBitmap(a2, tileWidth, tileWidth, true);
                    a3Scaled = Bitmap.createScaledBitmap(a3, tileWidth, tileWidth, true);
                    a4Scaled = Bitmap.createScaledBitmap(a4, tileWidth, tileWidth, true);
                    a5Scaled = Bitmap.createScaledBitmap(a5, tileWidth, tileWidth, true);
                    a6Scaled = Bitmap.createScaledBitmap(a6, tileWidth, tileWidth, true);
                    a7Scaled = Bitmap.createScaledBitmap(a7, tileWidth, tileWidth, true);
                    a8Scaled = Bitmap.createScaledBitmap(a8, tileWidth, tileWidth, true);
                    a9Scaled = Bitmap.createScaledBitmap(a9, tileWidth, tileWidth, true);
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                gameLoop.SetRunning(false);
                gameLoop.getThreadGroup().interrupt();
            }
        });

        a1 = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
        a2 = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
        a3 = BitmapFactory.decodeResource(getResources(), R.drawable.a3);
        a4 = BitmapFactory.decodeResource(getResources(), R.drawable.a4);
        a5 = BitmapFactory.decodeResource(getResources(), R.drawable.a5);
        a6 = BitmapFactory.decodeResource(getResources(), R.drawable.a6);
        a7 = BitmapFactory.decodeResource(getResources(), R.drawable.a7);
        a8 = BitmapFactory.decodeResource(getResources(), R.drawable.a8);
        a9 = BitmapFactory.decodeResource(getResources(), R.drawable.a9);

        DisplayMetrics dm = new DisplayMetrics();
        dm = Resources.getSystem().getDisplayMetrics();
        setScreenDimensions(dm.widthPixels, dm.heightPixels);

        a1Scaled = Bitmap.createScaledBitmap(a1, tileWidth, tileWidth, true);
        a2Scaled = Bitmap.createScaledBitmap(a2, tileWidth, tileWidth, true);
        a3Scaled = Bitmap.createScaledBitmap(a3, tileWidth, tileWidth, true);
        a4Scaled = Bitmap.createScaledBitmap(a4, tileWidth, tileWidth, true);
        a5Scaled = Bitmap.createScaledBitmap(a5, tileWidth, tileWidth, true);
        a6Scaled = Bitmap.createScaledBitmap(a6, tileWidth, tileWidth, true);
        a7Scaled = Bitmap.createScaledBitmap(a7, tileWidth, tileWidth, true);
        a8Scaled = Bitmap.createScaledBitmap(a8, tileWidth, tileWidth, true);
        a9Scaled = Bitmap.createScaledBitmap(a9, tileWidth, tileWidth, true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(canvas != null){
            Bitmap selectedTileBitmap = null;
            canvas.drawColor(Color.BLACK);
            for(int i = 0; i < Tiles.size(); i++){
                Bitmap b;
                Tile t = Tiles.get(i);
                switch (t.Type){
                    case 0: b = a1Scaled;
                        break;
                    case 1: b = a2Scaled;
                        break;
                    case 2: b = a3Scaled;
                        break;
                    case 3: b = a4Scaled;
                        break;
                    case 4: b = a5Scaled;
                        break;
                    case 5: b = a6Scaled;
                        break;
                    case 6: b = a7Scaled;
                        break;
                    case 7: b = a8Scaled;
                        break;
                    case 8: b = a9Scaled;
                        break;
                    default:
                        b = a1Scaled;
                }
                if(SelectedTileIndex != i) {
                    canvas.drawBitmap(b, screenDw + t.Column * tileWidth + t.dX, screenDh + t.Row * tileWidth + t.dY, null);
                } else {
                    selectedTileBitmap = b;
                }
            }
            if(SelectedTileIndex >= 0 && selectedTileBitmap != null){
                Tile t = Tiles.get(SelectedTileIndex);
                canvas.drawBitmap(selectedTileBitmap, screenDw + t.Column * tileWidth + t.dX, screenDh + t.Row * tileWidth + t.dY, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(SelectedTileIndex >= 0){
                    Tile t = Tiles.get(SelectedTileIndex);
                    t.dX = t.dX + (x - prevX);
                    t.dY = t.dY + (y - prevY);
                    Tiles.set(SelectedTileIndex, t);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                moveTileCol = Math.min((int)Math.floor((x - screenDw) / tileWidth), COLUMNS - 1);
                moveTileRow = Math.min((int)Math.floor((y - screenDh) / tileWidth), COLUMNS - 1);
                for(int i = 0; i < Tiles.size(); i++){
                    Tile t = Tiles.get(i);
                    if(t.Column == moveTileCol && t.Row == moveTileRow){
                        SelectedTileIndex = i;
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(SelectedTileIndex >= 0) {
                    Tile t = Tiles.get(SelectedTileIndex);
                    t.dX = 0;
                    t.dY = 0;
                    Tiles.set(SelectedTileIndex, t);
                    SelectedTileIndex = -1;
                }
                break;
        }

        prevX = x;
        prevY = y;

        return true;//super.onTouchEvent(event);
    }

    private void setScreenDimensions(int width, int height){
        sw = width;
        sh = height;
        tileWidth = Math.min(sw, sh) / COLUMNS;
        if (sw > sh){
            screenDh = 0;
            screenDw = (sw - sh)/2;
        } else {
            screenDw = 0;
            screenDh = (sh - sw)/2;
        }
    }
}
