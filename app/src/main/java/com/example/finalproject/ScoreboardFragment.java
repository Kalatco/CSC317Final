package com.example.finalproject;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class ScoreboardFragment extends Fragment {

    private ListView topScoreView = null;
    private TextView userScoreView = null;
    private Button uploadScoreButton = null;
    private Button shareScoreButton = null;
    private Button homeButton = null;
    private Button playAgainButton = null;
    private EditText usernameInput = null;

    private String serverAddress = "http://68.183.119.17:8001/flapperBird/api/scores";

    private final String[] SCORE_LIST_ITEM_FROM = {"position", "name", "score"};
    private final int[] SCORE_LIST_ITEM_TO = {R.id.article_item_position, R.id.article_item_title, R.id.article_item_author};

    private String userScore = "0";
    private String usernameStr = "unknown";

    public ScoreboardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        // Set views
        topScoreView = (ListView) rootView.findViewById(R.id.top_score_list);
        userScoreView = (TextView) rootView.findViewById(R.id.user_score);
        uploadScoreButton = (Button) rootView.findViewById(R.id.upload_score);
        shareScoreButton = (Button) rootView.findViewById(R.id.share_score);
        homeButton = (Button) rootView.findViewById(R.id.home_page);
        playAgainButton = (Button) rootView.findViewById(R.id.play_again);
        usernameInput = (EditText) rootView.findViewById(R.id.username_input);

        // Accept user score from PlayGameFragment
        //userScore = getArguments().getString("SCORE");
        userScore = "0";
        userScoreView.setText(userScore);


        // Set button functionality
        uploadScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("UPLOAD SCORE");
                // Upload score to server
                if (userScore.equals("0")) return;
                try {
                    String s = new AsyncUploadScore().execute().get();
                    // Get top scores from our scores API when AsyncUploadScore is finished
                    new AsyncStopScoresFetcher().execute();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        shareScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("SHARE SCORE");
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_layout_container, new MainMenuFragment()).addToBackStack("Fragment");
                ft.commit();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a game intent
                Intent intent = new Intent(getActivity(), PlayGameActivity.class);
                startActivity(intent);
            }
        });


        // Get top scores from our scores API
        new AsyncStopScoresFetcher().execute();

        return rootView;
    }

    private class AsyncStopScoresFetcher extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                // Connect to URL
                URL url = new URL(serverAddress);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();
                String inputLine = reader.readLine();

                // Read the API response
                while(inputLine != null) {
                    buffer.append(inputLine + "\n");
                    inputLine = reader.readLine();
                }

                // Return the API response string
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);

            // If the API response is valid, go through request and update ListView in XML.
            if (result != null && !result.trim().isEmpty()) {
                try {
                    JSONArray articleObjects = new JSONArray(result);

                    ArrayList<HashMap<String, String>> topScoresList = new ArrayList<>();

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("position", "position");
                    hashMap.put("name", "name");
                    hashMap.put("score", "score");
                    topScoresList.add(hashMap);

                    // Prepare to display items by extracting the source and name of each article
                    for (int x = 0; x < articleObjects.length(); x++) {
                        JSONObject dataItem = articleObjects.getJSONObject(x);

                        String username = "null";
                        if (dataItem.has("username")) {
                            username = dataItem.getString("username");
                        }

                        String score = "null";
                        if (dataItem.has("score")) {
                            score = dataItem.getString("score");
                        }

                        // Add items to simpleView, position is based off of the current line being read
                        hashMap = new HashMap<>();
                        hashMap.put("position", Integer.toString(x+1));
                        hashMap.put("name", username);
                        hashMap.put("score", score);
                        topScoresList.add(hashMap);
                    }

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), topScoresList, R.layout.top_score_item, SCORE_LIST_ITEM_FROM, SCORE_LIST_ITEM_TO);
                    topScoreView.setAdapter(simpleAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class AsyncUploadScore extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            // Get username, otherwise set as unknown
            String tempString = usernameInput.getText().toString();
            if(tempString.length() > 0) {
                usernameStr = tempString;
            }

            try {
                // Builds a HTTP POST request and sends JSON to the server
                URL url = new URL(serverAddress);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("username", usernameStr);
                jsonParam.put("score", userScore);

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}