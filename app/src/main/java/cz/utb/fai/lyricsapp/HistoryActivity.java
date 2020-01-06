package cz.utb.fai.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<String> artistList = new ArrayList<String>();
    private ArrayList<String> songList = new ArrayList<String>();
    private ArrayList<String> lyricsList = new ArrayList<String>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        context = this;

        loadData();
        prepareRecyclerView();
    }

    public void showLyrics(View view){
        Intent intent = new Intent(context, LyricsActivity.class);
        Bundle bundle = new Bundle();

        TextView artistView = view.findViewById(R.id.artistHistoryLabel);
        TextView songView = view.findViewById(R.id.songHistoryLabel);
        TextView lyricsView = view.findViewById(R.id.songId);

        bundle.putString("artist", artistView.getText().toString());
        bundle.putString("song", songView.getText().toString());
        int position = Integer.parseInt(lyricsView.getText().toString());
        bundle.putString("lyrics", lyricsList.get(position));

        intent.putExtras(bundle);
        startActivity(intent);
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
            String artist = sharedPreferences.getString(artistKeyInternal + i, "");
            String song = sharedPreferences.getString(songKeyInternal + i, "");
            String lyrics = sharedPreferences.getString(lyricsKeyInternal + i, "");

            artistList.add(artist);
            songList.add(song);
            lyricsList.add(lyrics);
        }
    }

    private void prepareRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(artistList, songList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
