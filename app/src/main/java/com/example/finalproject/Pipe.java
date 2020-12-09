package com.example.finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Pipe {


    private Bitmap pipeImage;
    public int x, y;

    private final int IMAGE_HEIGHT = 1600;
    private final int IMAGE_WIDTH = 150;


    public Pipe(Resources resources, int drawableID) {

        // Build bitmap to fit screen
        Bitmap tempImage = BitmapFactory.decodeResource(resources, drawableID);
        this.pipeImage = Bitmap.createScaledBitmap(tempImage, IMAGE_WIDTH, IMAGE_HEIGHT, false);

        // Prepare image position
        this.x = 2000;
        this.y = 0;

    }

    public Bitmap getPipe() {
        this.x -= 15;
        return this.pipeImage;
    }

    public void setY(int y) { this.y = y; }

    public void setX(int x) {
        this.x = x;
    }


}
