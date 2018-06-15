package com.shageev.pavel.match3;

import android.graphics.Canvas;

public class GameLoop extends Thread {
    private GameView view;
    static private long FPS = 30;
    private boolean running = false;
    boolean isPaused;

    public GameLoop(GameView view){
        this.view = view;
    }

    public void SetRunning(boolean run){
        running = run;
    }

    public void SetPause(boolean pause){
        synchronized (view.getHolder()){
            isPaused = pause;
        }
    }

    @Override
    public void run() {
        super.run();
        long frameMs = 1000/FPS;
        long startTime = 0;
        long sleepTime;

        while(running){
            if(isPaused){
                try {
                    this.sleep(50);
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            } else {
                Canvas c = null;
                startTime = System.currentTimeMillis();
                try {
                    c = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()){
                        view.draw(c);
                    }
                } finally {
                    if(c != null){
                        view.getHolder().unlockCanvasAndPost(c);
                    }
                }
            }

            sleepTime = frameMs - (System.currentTimeMillis() - startTime);

            try{
                if(sleepTime > 0){
                    sleep(sleepTime);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
