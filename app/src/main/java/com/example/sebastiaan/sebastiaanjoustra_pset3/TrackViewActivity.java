package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TrackViewActivity extends AppCompatActivity {

    TextView tvTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view);

        Intent previousIntent = getIntent();
        //String trackJSON = previousIntent.getStringExtra("trackInfo");
        Track track = (Track) previousIntent.getSerializableExtra("track");

        tvTrack = (TextView) findViewById(R.id.tvTrack);

        tvTrack.setText(track.getArtist());
    }
}
