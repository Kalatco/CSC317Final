package com.example.finalproject;

import android.app.Activity;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayGameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // to make fullscreen, works without this
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point(); // used to hold the dimensions of the screen
        getWindowManager().getDefaultDisplay().getSize(point);

        // initialize gameView object
        gameView = new GameView(this, point.x, point.y);


        // Check for game over in gameView object
        new AsyncCheckGameOver().execute();

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    private class AsyncCheckGameOver extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            while(!gameView.checkIfGameOver()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Game Over! now do something from PlayGameActivity");
            System.out.println("User score is: " + gameView.getGameOverScore());

            return null;
        }
    }

}