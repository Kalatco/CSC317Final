package com.example.finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.Random;

public class PipeLayer {

    public Pipe topPipe, bottomPipe;
    public int y, screenY;
    private final int OPENING_DEVIATION = 350;

    public PipeLayer(Resources resources, int screenY) {
        // make pipes
        this.topPipe = new Pipe(resources, R.drawable.top_pipe);
        this.bottomPipe = new Pipe(resources, R.drawable.bottom_pipe);
        this.screenY = screenY;
        setNewPipes();
    }

    private void setNewPipes() {
        selectRandomYPos();
        //topPipe.setY(-1500); // this displays top pipe all the way at the top (all way to bottom is -700)
        //bottomPipe.setY(900);// this displays bottom pipe all the way at the bottom (all way to top is 0)

        topPipe.setY(y-1500);
        topPipe.setX(2000);
        bottomPipe.setY(y+OPENING_DEVIATION);
        bottomPipe.setX(2000);
    }

    public Bitmap getTopPipe() {
        if(topPipe.x < -300) {
            setNewPipes();
        }
        return topPipe.getPipe();
    }

    public Bitmap getBottomPipe() {
        return bottomPipe.getPipe();
    }

    // selects a value between 0 and screenY
    private void selectRandomYPos() {
        Random random = new Random();
        int yVal = random.nextInt(600);
        this.y =  yVal;
    }

    public boolean isInvalidPass(int birdX, int birdY) {
        //If close to pipe image
        if (birdX == (bottomPipe.x-5)) {
            System.out.println("PRINTING BIRD Y: " + Integer.toString(birdY) + " BOTTOM PIPE Y: "+ Integer.toString(bottomPipe.y));
            System.out.println("AND TOP PIPE Y IS " + Integer.toString(topPipe.y+1600));
            if(birdY > bottomPipe.y){
                return true;
            }
        }
        return false;
    }


}
