package com.example.finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bird {

    public int x, y, width, height, birdFlag = 0;
    public boolean isGoingUp = false;
    private Bitmap bird1, bird2;

    public Bird(int screenY, Resources resources) {
        bird1 = BitmapFactory.decodeResource(resources, R.drawable.bird_fly_one);
        bird2 = BitmapFactory.decodeResource(resources, R.drawable.bird_fly_two);



        // this sets how big bird will be displayed as
        width = bird1.getWidth() / 3;
        height = bird1.getHeight() / 3;

        //width *= (int) GameView.screenRatioX;
        //height *= (int) GameView.screenRatioY;

        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);


        y = screenY / 2;
        x = 60; // starting x position
        // x2 and y2 will be used for checking bounds

    }

    // returns the bitmap of the bird
    public Bitmap getBird () {
        if(isGoingUp){
            isGoingUp = false;
            birdFlag ++;
            return bird2;
        }
        // this make bird go down
        y+= 14;
        //System.out.println("bird update Y: " + y);
        return bird1;
    }

}
