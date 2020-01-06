package cz.utb.fai.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<String> artistList = new ArrayList<String>();
    private ArrayList<String> songList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        loadData();
        prepareRecyclerView();
    }

    private void loadData(){
        String lyricsFileName = "lyricsFile";
        String artistKeyInternal = "artistKeyInternal";
        String songKeyInternal = "songKeyInternal";
        String lyricsKeyInternal = "lyricsKeyInternal";
        String countKeyInternal = "count";

        SharedPreferences sharedPreferences = this.getSharedPreferences(lyricsFileName, MODE_PRIVATE);

        int countOfSongs = (sharedPreferences.getInt(countKeyInternal, 0));

        for (int i = countOfSongs - 1; i >= 0; --i){
            String artist = sharedPreferences.getString(artistKeyInternal + i, "Empty");
            String song = sharedPreferences.getString(songKeyInternal + i, "Empty");

            artistList.add(artist);
            songList.add(song);
        }
    }

    private void prepareRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(artistList, songList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
