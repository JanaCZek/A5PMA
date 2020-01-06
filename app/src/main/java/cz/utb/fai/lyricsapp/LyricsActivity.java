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
        setContentView(R.layout.activity_lyrics);

        Bundle bundle = getIntent().getExtras();

        artist = findViewById(R.id.artistTextView);
        song = findViewById(R.id.songTextView);
        lyrics = findViewById(R.id.lyricsTextView);

        artist.setText(bundle.getString("artist"));
        song.setText(bundle.getString("song"));
        lyrics.setText(bundle.getString("lyrics"));
    }

    @Override
    protected void onStop() {
        String lyricsFileName = "lyricsFile";
        String artistKeyInternal = "artistKeyInternal";
        String songKeyInternal = "songKeyInternal";
        String lyricsKeyInternal = "lyricsKeyInternal";
        String countKeyInternal = "count";

        SharedPreferences sharedPreferences = this.getSharedPreferences(lyricsFileName, MODE_PRIVATE);
        boolean isInHistory = checkHistory(sharedPreferences, artist.getText(), song.getText());

        if (isInHistory) {
            super.onStop();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        int count = sharedPreferences.getInt(countKeyInternal, 0);

        editor.putString(artistKeyInternal + count, artist.getText().toString());
        editor.putString(songKeyInternal + count, song.getText().toString());
        editor.putString(lyricsKeyInternal + count, lyrics.getText().toString());

        editor.putInt(countKeyInternal, count + 1);
        editor.apply();

        super.onStop();
    }

    private boolean checkHistory(SharedPreferences sharedPreferences, CharSequence artist, CharSequence song) {
        String artistKeyInternal = "artistKeyInternal";
        String songKeyInternal = "songKeyInternal";
        String countKeyInternal = "count";

        int count = sharedPreferences.getInt(countKeyInternal, 0);

        for (int i = count - 1; i >= 0; --i){
            String artistValue = sharedPreferences.getString(artistKeyInternal + i, "");
            String songValue = sharedPreferences.getString(songKeyInternal + i, "");

            if (artist.equals(artistValue) && song.equals(songValue)) return true;
        }

        return false;
    }
}
