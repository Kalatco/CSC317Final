package com.example.finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int screenSizeX = 0;
    int screenSizeY = 0;
    Bitmap background;

    public Background(int screenSizeX, int screenSizeY, Resources res) {
        background = BitmapFactory.decodeResource(res , R.drawable.mario_background); // CHANGE DRAWABLE HERE FOR NEW BACKGROUND
        background = Bitmap.createScaledBitmap(background, screenSizeX, screenSizeY, false);
    }

}
