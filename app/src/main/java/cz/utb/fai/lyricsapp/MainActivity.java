package cz.utb.fai.lyricsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
    }
}
