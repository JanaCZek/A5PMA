package cz.utb.fai.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class LyricsActivity extends AppCompatActivity {

    private TextView artist;
    private TextView song;
    private TextView lyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        artist.setText(savedInstanceState.getString("artist"));
        song.setText(savedInstanceState.getString("song"));
        lyrics.setText(savedInstanceState.getString("lyrics"));
    }

    @Override
    protected void onStop() {
        String lyricsFileName = "lyricsFile";
        String artistKeyInternal = "artistKeyInternal";
        String songKeyInternal = "songKeyInternal";
        String lyricsKeyInternal = "lyricsKeyInternal";
        String countKeyInternal = "count";

        SharedPreferences sharedPreferences = this.getSharedPreferences(lyricsFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int count = sharedPreferences.getInt(countKeyInternal, 0);

        editor.putString(artistKeyInternal, artist.getText().toString() + count);
        editor.putString(songKeyInternal, song.getText().toString()+ count);
        editor.putString(lyricsKeyInternal, lyrics.getText().toString() + count);

        editor.putInt(countKeyInternal, count + 1);

        super.onStop();
    }
}
