package com.shageev.pavel.match3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.shageev.pavel.match3.GameMode.*;

enum GameMode{Selection, Swap, SwapBack, Explode, FallDown, OnHold}

public class GameView extends SurfaceView {
    GameLoop gameLoop;
    int sw,sh, screenDw, screenDh;
    int tileWidth;
    private GameView thisView;
    private int COLUMNS = 7;
    private int MOVE_THRESHOLD = 20;
    private int SWAP_STEP = 700;
    private int JUMP_SPEED = 15;
    private int EXPLODE_SPEED = 400;
    private int EXPLODE_END = 100;
    int[][] gameField;
    GameField gField;
    int moveTileCol, moveTileRow;
    int prevX, prevY;
    int SelectedTileIndex;
    Rect transformRect = new Rect();
    private GameMode gameMode;
    Tile swapFrom, swapTo;

    public GameView(Context context){
        super(context);
        thisView = this;
        SurfaceHolder holder = getHolder();

        gameField = new int[][]{
                { 1, 2, 1, 0, 1, 1, 0, 2 },
                { 2, 1, 2, 1, 2, 0, 1, 2 },
                { 8, 2, 1, 0, 2, 3, 4, 1 },
                { 1, 3, 4, 6, 4, 1, 2, 1 },
                { 0, 8, 3, 2, 0, 6, 7, 3 },
                { 1, 2, 1, 0, 6, 1, 0, 7 },
                { 2, 3, 5, 1, 2, 0, 1, 2 },
                { 8, 2, 1, 0, 2, 3, 4, 1 }
        };

        gField = new GameField(COLUMNS);
        gField.init(gameField);

        SelectedTileIndex = -1;

        moveTileCol = COLUMNS;
        moveTileRow = COLUMNS;

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoop = new GameLoop(thisView);
                gameLoop.SetRunning(true);
                gameLoop.start();
                gameMode = Selection;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
                setScreenDimensions(w, h);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                gameLoop.SetRunning(false);
                gameLoop.getThreadGroup().interrupt();
                gameMode = OnHold;
            }
        });

        DisplayMetrics dm;
        dm = Resources.getSystem().getDisplayMetrics();
        setScreenDimensions(dm.widthPixels, dm.heightPixels);
    }

    protected void update(double modifier){
        int x1, y1, x2, y2;
        int x1d, y1d, x2d, y2d;
        int x1n, y1n, x2n, y2n;
        int direction;
        switch(gameMode) {
            case Selection:
                for (int i = 0; i < gField.Tiles.size(); i++) {
                    Tile t = gField.Tiles.get(i);
                    if (t.Selected) {
                        t.jumpPhase += modifier * JUMP_SPEED;
                        gField.Tiles.set(i, t);
                    }
                }
                break;
            case Swap:
                //current tile positions
                x1 = screenDw + swapFrom.Column * tileWidth + swapFrom.dX;
                y1 = screenDh + swapFrom.Row * tileWidth + swapFrom.dY;
                x2 = screenDw + swapTo.Column * tileWidth + swapTo.dX;
                y2 = screenDh + swapTo.Row * tileWidth + swapTo.dY;

                //tile destinations
                x1d = screenDw + swapTo.Column * tileWidth;
                y1d = screenDh + swapTo.Row * tileWidth;
                x2d = screenDw + swapFrom.Column * tileWidth;
                y2d = screenDh + swapFrom.Row * tileWidth;

                //next step positions
                if(Math.abs(x1d - x1) <= SWAP_STEP * modifier){
                    x1n = x1d;
                } else {
                    if(x1 > x1d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    x1n = x1 + (int)(SWAP_STEP * direction * modifier);
                }
                if(Math.abs(y1d - y1) <= SWAP_STEP * modifier){
                    y1n = y1d;
                } else {
                    if(y1 > y1d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    y1n = y1 + (int)(SWAP_STEP * direction * modifier);
                }
                if(Math.abs(x2d - x2) <= SWAP_STEP * modifier){
                    x2n = x2d;
                } else {
                    if(x2 > x2d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    x2n = x2 + (int)(SWAP_STEP * direction * modifier);
                }
                if(Math.abs(y2d - y2) <= SWAP_STEP * modifier){
                    y2n = y2d;
                } else {
                    if(y2 > y2d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    y2n = y2 + (int)(SWAP_STEP * direction * modifier);
                }

                swapFrom.dX += x1n - x1;
                swapFrom.dY += y1n - y1;
                swapTo.dX += x2n - x2;
                swapTo.dY += y2n - y2;
                gField.Tiles.set(gField.Tiles.indexOf(swapFrom), swapFrom);
                gField.Tiles.set(gField.Tiles.indexOf(swapTo), swapTo);

                if(x1d == x1n && y1d == y1n && x2d == x2n && y2d == y2n){
                    int fromDx = swapFrom.dX;
                    int fromDy = swapFrom.dY;
                    int toDx = swapTo.dX;
                    int toDy = swapTo.dY;
                    gField.swap(swapFrom, swapTo);
                    if(gField.match()){
                        //убрать лишние
                        gameMode = Explode;
                        SelectedTileIndex = -1;
                    } else {
                        gField.swap(swapFrom, swapTo);
                        swapFrom.dX = fromDx;
                        swapFrom.dY = fromDy;
                        swapTo.dX = toDx;
                        swapTo.dY = toDy;
                        gField.Tiles.set(gField.Tiles.indexOf(swapFrom), swapFrom);
                        gField.Tiles.set(gField.Tiles.indexOf(swapTo), swapTo);
                        gameMode = SwapBack;
                    }
                }
                break;
            case SwapBack:
                //current tile positions
                x1 = screenDw + swapFrom.Column * tileWidth + swapFrom.dX;
                y1 = screenDh + swapFrom.Row * tileWidth + swapFrom.dY;
                x2 = screenDw + swapTo.Column * tileWidth + swapTo.dX;
                y2 = screenDh + swapTo.Row * tileWidth + swapTo.dY;

                //tile destinations
                x1d = screenDw + swapFrom.Column * tileWidth;
                y1d = screenDh + swapFrom.Row * tileWidth;
                x2d = screenDw + swapTo.Column * tileWidth;
                y2d = screenDh + swapTo.Row * tileWidth;

                //next step positions
                if(Math.abs(x1d - x1) <= SWAP_STEP * modifier){
                    x1n = x1d;
                } else {
                    if(x1 > x1d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    x1n = x1 + (int)(SWAP_STEP * direction * modifier);
                }
                if(Math.abs(y1d - y1) <= SWAP_STEP * modifier){
                    y1n = y1d;
                } else {
                    if(y1 > y1d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    y1n = y1 + (int)(SWAP_STEP * direction * modifier);
                }
                if(Math.abs(x2d - x2) <= SWAP_STEP * modifier){
                    x2n = x2d;
                } else {
                    if(x2 > x2d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    x2n = x2 + (int)(SWAP_STEP * direction * modifier);
                }
                if(Math.abs(y2d - y2) <= SWAP_STEP * modifier){
                    y2n = y2d;
                } else {
                    if(y2 > y2d){
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    y2n = y2 + (int)(SWAP_STEP * direction * modifier);
                }

                swapFrom.dX += x1n - x1;
                swapFrom.dY += y1n - y1;
                swapTo.dX += x2n - x2;
                swapTo.dY += y2n - y2;
                gField.Tiles.set(gField.Tiles.indexOf(swapFrom), swapFrom);
                gField.Tiles.set(gField.Tiles.indexOf(swapTo), swapTo);

                if(x1d == x1n && y1d == y1n && x2d == x2n && y2d == y2n){
                    gField.clearSelected();

                    gameMode = Selection;
                }
                break;
            case Explode:
                boolean finishExplosions = true;
                for(int i = 0; i < COLUMNS; i++){
                    for(int j = 0; j < COLUMNS; j++){
                        if(gField.getRemoveState(i, j)){
                            Tile t = gField.getTile(i, j);
                            t.explodePhase += EXPLODE_SPEED * modifier;
                            if(t.explodePhase >= EXPLODE_END){
                                t.explodePhase = EXPLODE_END;
                            } else {
                                finishExplosions = false;
                            }
                            gField.Tiles.set(gField.Tiles.indexOf(t), t);

                        }
                    }
                }
                if(finishExplosions){
                    gField.moveDown(tileWidth);
                    gameMode = FallDown;
                }
                break;
            case FallDown:
                //TODO: оставшиеся мячи падают на место взорванных
                if(!gField.fall(modifier)) {
                    if(gField.match()){
                        gameMode = Explode;
                    } else {
                        gameMode = Selection;
                    }
                }
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(canvas != null && ResourceManager.getInstance().scaledImagesLoaded){
            canvas.drawColor(Color.BLACK);
            for(int i = 0; i < gField.Tiles.size(); i++){
                Bitmap b;
                Tile t = gField.Tiles.get(i);
                double jumpModifier = 0;

                if(t.Selected){
                    jumpModifier = Math.sin(t.jumpPhase) * tileWidth / 6;
                }
                b = ResourceManager.getInstance().tileImages.get(t.Type);

                if(t.explodePhase > 0){
                    transformRect.set(
                            screenDw + t.Column * tileWidth + t.dX + (int)(tileWidth / 2 * t.explodePhase / EXPLODE_END),
                            screenDh + t.Row * tileWidth + (int)(tileWidth / 2 * t.explodePhase / EXPLODE_END) + t.dY,
                            screenDw + t.Column * tileWidth + tileWidth + t.dX - (int)(tileWidth / 2 * t.explodePhase / EXPLODE_END),
                            screenDh + t.Row * tileWidth + tileWidth - (int)(tileWidth / 2 * t.explodePhase / EXPLODE_END) + t.dY);
                    canvas.drawBitmap(b, null, transformRect, null);
                } else if(jumpModifier <= 0) {
                    canvas.drawBitmap(b, screenDw + t.Column * tileWidth + t.dX, screenDh + t.Row * tileWidth + t.dY + (int) jumpModifier, null);
                } else {
                    transformRect.set(
                            screenDw + t.Column * tileWidth + t.dX - (int)(jumpModifier / 4),
                            screenDh + t.Row * tileWidth + (int)(jumpModifier / 2) + t.dY,
                            screenDw + t.Column * tileWidth + tileWidth + t.dX + (int)(jumpModifier / 4),
                            screenDh + t.Row * tileWidth + tileWidth + t.dY);
                    canvas.drawBitmap(b, null, transformRect, null);
                }
            }
            //show some internal arrays
//            int size = 20;
//            Paint textPaint = new Paint();
//            textPaint.setColor(Color.WHITE);
//            textPaint.setTextSize(size);
//            for(int i = 0; i < COLUMNS; i++){
//                for(int j = 0; j < COLUMNS; j++){
//                    canvas.drawText(String.valueOf(gField.getTileId(j, i)), i * size * 2, 3* size +j * size * 2, textPaint);
//
//                    canvas.drawText(String.valueOf(gField.getTileType(j,i)), size * 2 * (COLUMNS + 2)+ i * size * 2, 3*size + j * size * 2, textPaint);
//
//                    for(int k = 0; k < gField.Tiles.size(); k++){
//                        Tile t = gField.Tiles.get(k);
//                        if(t.Column == i && t.Row == j){
//                            canvas.drawText(String.valueOf(k), size * 2 * (2*COLUMNS + 4)+ i * size * 2, 3*size + j * size * 2, textPaint);
//                            canvas.drawText(String.valueOf(t.Type), size * 2 * (3*COLUMNS + 6)+ i * size * 2, 3*size + j * size * 2, textPaint);
//
//                        }
//                    }
//                }
//            }
        }
    }

    private void swapMode(Tile from, Tile to){
        gameMode = Swap;
        swapFrom = from;
        swapTo = to;
        swapFrom.jumpPhase = 0;
        swapTo.jumpPhase = 0;
        gField.Tiles.set(gField.Tiles.indexOf(swapFrom), swapFrom);
        gField.Tiles.set(gField.Tiles.indexOf(swapTo), swapTo);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(SelectedTileIndex >= 0 && gameMode == Selection){
                    Tile t = gField.Tiles.get(SelectedTileIndex);
                    t.dX = t.dX + (x - prevX);
                    t.dY = t.dY + (y - prevY);
                    Tile moveTo = gField.getNeighbour(t.Column, t.Row, t.dX, t.dY, MOVE_THRESHOLD);
                    if(Math.abs(t.dX) > MOVE_THRESHOLD || Math.abs(t.dY) > MOVE_THRESHOLD) {
                        t.Selected = false;
                        if(moveTo != null){
                            swapMode(t, moveTo);
                        }
                    }
                    gField.Tiles.set(SelectedTileIndex, t);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(gameMode == Selection) {
                    moveTileCol = (int) Math.floor((x - screenDw) / tileWidth);
                    moveTileRow = (int) Math.floor((y - screenDh) / tileWidth);

                    for (int i = 0; i < gField.Tiles.size(); i++) {
                        Tile t = gField.Tiles.get(i);
                        if (t.Column == moveTileCol && t.Row == moveTileRow) {
                            t.Selected = !t.Selected;
                            t.jumpPhase = 0;
                            gField.Tiles.set(i, t);
                        } else {
                            if (t.Selected) {
                                if(gField.isNeighbour(t, moveTileRow, moveTileCol)) {
                                    swapMode(t, gField.getTile(moveTileRow, moveTileCol));
                                } else {
                                    t.Selected = false;
                                    t.jumpPhase = 0;
                                    gField.Tiles.set(i, t);
                                }
                            }
                        }

                    }
                    SelectedTileIndex = gField.getTileId(moveTileRow, moveTileCol);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(SelectedTileIndex >= 0 && gameMode == Selection) {
                    Tile t = gField.Tiles.get(SelectedTileIndex);

                    t.dX = 0;
                    t.dY = 0;
                    gField.Tiles.set(SelectedTileIndex, t);

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
