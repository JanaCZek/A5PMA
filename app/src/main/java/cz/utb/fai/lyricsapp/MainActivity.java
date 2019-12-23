package cz.utb.fai.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText songNameEdit;
    EditText artistNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songNameEdit = findViewById(R.id.songName);
        artistNameEdit = findViewById(R.id.artistName);
    }

    public void searchLyrics(View v){
        String songName = songNameEdit.getText().toString();
        String artistName = artistNameEdit.getText().toString();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL baseURL = new URL("https://mourits.xyz:2096/?q=Bohemian%20Rhapsody");

                    HttpsURLConnection connection = (HttpsURLConnection) baseURL.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json");

                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(responseBody, "UTF-8");
                    BufferedReader br = new BufferedReader(reader);
                    StringBuilder sb = new StringBuilder();
                    String input = "";

                    while ((input = br.readLine()) != null){
                        sb.append(input);
                    }
                    JSONObject object = new JSONObject(sb.toString());

                    boolean success = object.getBoolean("success");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
