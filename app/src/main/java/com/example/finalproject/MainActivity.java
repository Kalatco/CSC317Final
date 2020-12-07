package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainMenuFragment mainMenu = new MainMenuFragment();
        Bundle args = new Bundle();
        mainMenu.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout_container , mainMenu);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();

        // Start up scoreboard with game over score.
        ScoreboardFragment scoreMenu = new ScoreboardFragment();
        Bundle args = new Bundle();
        args.putString("score", extras.getString("score"));
        scoreMenu.setArguments(args);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout_container, scoreMenu);
        transaction.commit();
    }
}