package cz.utb.fai.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    EditText songNameEdit;
    EditText artistNameEdit;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songNameEdit = findViewById(R.id.songName);
        artistNameEdit = findViewById(R.id.artistName);
        progressBar = findViewById(R.id.progressBar);

        context = this;
    }

    public void searchLyrics(View v) {
        final String songName = songNameEdit.getText().toString();
        final String artistName = artistNameEdit.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        try {
            URL url;
            String urlString = "https://mourits.xyz:2096/";

            if (songName.isEmpty() == false && artistName.isEmpty() == false) {
                urlString += ("?a=" + artistName + "&s=" + songName);
            } else if (songName.isEmpty() == false && artistName.isEmpty()) {
                urlString += ("?q=" + songName);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Song name cannot be empty", Toast.LENGTH_LONG).show();

                return;
            }

            url = new URL(urlString);

            //https://stackoverflow.com/questions/6511880/how-to-parse-a-json-input-stream
            ApiCall apiCall = new ApiCall();
            apiCall.execute(url);

            JSONObject jsonResult = apiCall.get();

            boolean success = jsonResult.getBoolean("success");

            progressBar.setVisibility(View.GONE);

            if (success) {
                showLyrics(jsonResult);
            }
            else {
                Toast.makeText(this, "Lyrics not found", Toast.LENGTH_LONG).show();
                return;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Error occured", Toast.LENGTH_LONG).show();
    }

    public void viewHistory(View view) {
        Intent intent = new Intent(context, HistoryActivity.class);
        startActivity(intent);
    }

    private void showLyrics(JSONObject jsonResult) throws JSONException {
        Intent intent = new Intent(context, LyricsActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("artist", jsonResult.getString("artist"));
        bundle.putString("song", jsonResult.getString("song"));

        JSONObject lyricsResult = jsonResult.getJSONObject("result");
        bundle.putString("lyrics", lyricsResult.getString("lyrics"));

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class ApiCall extends AsyncTask<URL, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL[] urls) {

            URLConnection connection;
            try {
                connection = urls[0].openConnection();
                connection.setConnectTimeout(0);
                connection.setReadTimeout(0);

                connection.connect();

                InputStream responseBody = connection.getInputStream();

                InputStreamReader reader = new InputStreamReader(responseBody, "UTF-8");
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String input;

                while ((input = br.readLine()) != null) {
                    sb.append(input);
                }
                JSONObject jsonResult = new JSONObject(sb.toString());

                return jsonResult;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
