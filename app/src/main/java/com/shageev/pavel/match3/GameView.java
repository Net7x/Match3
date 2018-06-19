package com.shageev.pavel.match3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

        Tiles = new ArrayList<>();
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
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                gameLoop.SetRunning(false);
                gameLoop.getThreadGroup().interrupt();
            }
        });




        DisplayMetrics dm;
        dm = Resources.getSystem().getDisplayMetrics();
        setScreenDimensions(dm.widthPixels, dm.heightPixels);
    }

    protected void update(double modifier){
        for(int i = 0; i < Tiles.size(); i++){
            Tile t = Tiles.get(i);
            if(t.Selected){
                t.jumpPhase += modifier * 10 ;
                Tiles.set(i, t);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(canvas != null && ResourceManager.getInstance().scaledImagesLoaded){
            Bitmap selectedTileBitmap = null;
            canvas.drawColor(Color.BLACK);
            for(int i = 0; i < Tiles.size(); i++){
                Bitmap b;
                Tile t = Tiles.get(i);
                double jumpModifier = 0;
                if(t.Selected){
                    jumpModifier = Math.cos(t.jumpPhase) * tileWidth / 6;
                }
                b = ResourceManager.getInstance().tileImages.get(t.Type);
                if(SelectedTileIndex != i) {
                    if(jumpModifier < 0) {
                        canvas.drawBitmap(b, screenDw + t.Column * tileWidth + t.dX, screenDh + t.Row * tileWidth + t.dY + (int) jumpModifier, null);
                    } else {
                        canvas.drawBitmap(b, null,
                                new Rect(
                                        screenDw + t.Column * tileWidth + t.dX - (int)(jumpModifier / 4),
                                        screenDh + t.Row * tileWidth + (int)(jumpModifier / 2) + t.dY,
                                        screenDw + t.Column * tileWidth + tileWidth + t.dX + (int)(jumpModifier / 4),
                                        screenDh + t.Row * tileWidth + tileWidth + t.dY),
                                null);
                    }
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
                    t.Selected = false;
                    Tiles.set(SelectedTileIndex, t);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                moveTileCol = (int)Math.floor((x - screenDw) / tileWidth);
                moveTileRow = (int)Math.floor((y - screenDh) / tileWidth);

                for(int i = 0; i < Tiles.size(); i++){
                    Tile t = Tiles.get(i);
                    if(t.Selected){
                        t.Selected = false;
                        Tiles.set(i, t);
                    }
                }
                SelectedTileIndex = gField.getTileId(moveTileRow, moveTileCol);

                break;
            case MotionEvent.ACTION_UP:
                if(SelectedTileIndex >= 0) {
                    Tile t = Tiles.get(SelectedTileIndex);
                    if(Math.abs(t.dX)< 10  && Math.abs(t.dY) < 10) {
                        t.Selected = !t.Selected;
                        t.jumpPhase = 0;
                    }
                    else {
                        t.Selected = false;
                    }
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
        ResourceManager.getInstance().scaleImages(tileWidth);
        if (sw > sh){
            screenDh = 0;
            screenDw = (sw - sh)/2;
        } else {
            screenDw = 0;
            screenDh = (sh - sw)/2;
        }
    }
}
