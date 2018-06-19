package com.shageev.pavel.match3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<Bitmap> tileRawImages;
    public ArrayList<Bitmap> tileImages;
    private boolean rawImagesLoaded = false;
    public boolean scaledImagesLoaded = false;
    private int tileSize = 0;
    private Resources res;

    private static final ResourceManager INSTANCE = new ResourceManager();

    public void loadImages(Resources resources){
        res = resources;
        rawImagesLoaded = false;
        tileRawImages = new ArrayList<>();
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a1));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a2));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a3));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a4));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a5));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a6));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a7));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a8));
        tileRawImages.add(BitmapFactory.decodeResource(res, R.drawable.a9));
        rawImagesLoaded = true;
    }

    public void scaleImages(){
        if(tileSize > 0){
            scaleImages(tileSize);
        }
    }

    public void scaleImages(int size){
        scaledImagesLoaded = false;
        if(!rawImagesLoaded) {
            loadImages(res);
        }
        tileImages = new ArrayList<>();
        for (int i = 0; i < tileRawImages.size(); i++) {
            tileImages.add(Bitmap.createScaledBitmap(tileRawImages.get(i), size, size, true));
        }
        scaledImagesLoaded = true;
    }

    public static ResourceManager getInstance(){
        return INSTANCE;
    }
}
