package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements  Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    //public static float screenRatioX, screenRatioY;
    private int screenX, screenY;
    private Paint paint;
    private Bird bird;

    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);
        this.paint = new Paint();
        this.screenX = screenSizeX;
        this.screenY = screenSizeY;

//        this.screenRatioX = 1920f / screenX; // ratio to work with all screen sizes
//        this.screenRatioY = 1080f / screenY;

        this.bird = new Bird(screenY, getResources());

        this.background1 = new Background(screenSizeX, screenSizeY, getResources());
        this.background2 = new Background(screenSizeX, screenSizeY, getResources());

        background2.screenSizeX = screenSizeX;



    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    public void update() {

        background1.screenSizeX -= 10  ; // moves screen by 10 pixels every 17 milliseconds
        background2.screenSizeX -= 10  ;

        if(background1.screenSizeX + background1.background.getWidth() < 0) { // when background falls out of bounds
            background1.screenSizeX = screenX;
        }

        if(background2.screenSizeX + background2.background.getWidth() < 0) {
            background2.screenSizeX = screenX;
        }

        if(bird.isGoingUp){
            // make bird go up
            bird.y -= 15;
        }

    }

    // draw() will create and display the canvas for the game view
    public void draw() {
        if(getHolder().getSurface().isValid()) // this condition makes sure object is successfully initiated
        {
            Canvas canvas = getHolder().lockCanvas(); // returns current canvas
            // create canvas to be displayed
            canvas.drawBitmap(background1.background, background1.screenSizeX, background1.screenSizeY, paint);
            canvas.drawBitmap(background2.background, background2.screenSizeX, background2.screenSizeY, paint);

            canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);


            getHolder().unlockCanvasAndPost(canvas); // this shows the canvas
        }

    }

    public void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bird.isGoingUp = true;
                break;
        }

        return true;
    }
}