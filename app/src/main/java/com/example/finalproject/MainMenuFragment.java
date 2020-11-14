package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainMenuFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // get how to start game button, set listener
        ImageView startGame = view.findViewById(R.id.startButton);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new fragment
                PlayGameFragment playFrag = new PlayGameFragment();
                Bundle args = new Bundle();
                playFrag.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(((ViewGroup) getView().getParent()).getId() , playFrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        // get how to play button, set listener
        ImageView howToPlay = view.findViewById(R.id.howToPlayButton);
        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new fragment
                HowToPlayFragment howToFrag = new HowToPlayFragment();
                Bundle args = new Bundle();
                howToFrag.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(((ViewGroup) getView().getParent()).getId() , howToFrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        // get scoreboard button, set listener
        ImageView scoreboard = view.findViewById(R.id.scoreboardButton);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new fragment
                ScoreboardFragment scoreboardFrag = new ScoreboardFragment();
                Bundle args = new Bundle();
                scoreboardFrag.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(((ViewGroup) getView().getParent()).getId() , scoreboardFrag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return  view;
    }
}