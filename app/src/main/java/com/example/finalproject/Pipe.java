package com.example.finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Pipe {
    private Bitmap pipeImage;
    public int x, y;

    private final int VALID_PIPE_Y[] = {-100, -150, -200, -250, -300, -350, -400, -450, -500};
    private final int VALID_PIPE_OPENING[] = {740, 660, 580, 500, 420, 340, 280, 230, 190};
    private int chosenOpening = 0;
    private final int OPENING_DEVIATION = 160;

    private final int IMAGE_HEIGHT = 1600;
    private final int IMAGE_WIDTH = 150;


    public Pipe(Resources resources) {

        // Build bitmap to fit screen
        Bitmap tempImage = BitmapFactory.decodeResource(resources, R.drawable.pipe);
        this.pipeImage = Bitmap.createScaledBitmap(tempImage, IMAGE_WIDTH, IMAGE_HEIGHT, false);

        // Prepare image position
        this.x = 1800;
        this.y = selectRandomYPos();
    }

    public Bitmap getPipe() {

        if(this.x < -300) {
            this.y = selectRandomYPos();
            this.x = 1800;
        }

        this.x -= 15;
        return this.pipeImage;
    }


    private int selectRandomYPos() {
        Random random = new Random();
        int max = VALID_PIPE_Y.length-1;
        int min = 0;
        int pos = random.nextInt(max - min) + min;
        this.chosenOpening = VALID_PIPE_OPENING[pos];
        return VALID_PIPE_Y[pos];
    }

    public boolean isInvalidPass(int birdX, int birdY) {

        // If close to pipe image
        if (birdX == this.x) {

            System.out.println("pip Y: " + this.y);
            System.out.println("bird Y: " + birdY + " , opening: " + this.chosenOpening);

            if (birdY > this.chosenOpening-OPENING_DEVIATION && birdY < this.chosenOpening+OPENING_DEVIATION) {
                System.out.println("in range?");
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isValidPass(int birdX, int birdY) {
        if (birdX == this.x) {
            return birdY > this.chosenOpening-OPENING_DEVIATION && birdY < this.chosenOpening+OPENING_DEVIATION;
        }
        return false;
    }
}
