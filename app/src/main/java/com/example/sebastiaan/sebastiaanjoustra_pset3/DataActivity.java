package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DataActivity extends AppCompatActivity {

    ListView lvItems;
    ArrayList<String> trackArray = new ArrayList<String>();
    Track [] trackObjectsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        lvItems = (ListView) findViewById(R.id.lvItems);
        assert lvItems != null;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        trackObjectsArray = (Track[]) bundle.getSerializable("tracks");

        makeTrackArray();
        makeTrackAdapter();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), TrackViewActivity.class);
                intent.putExtra("track", trackObjectsArray[i]);
                startActivity(intent);
            }
        });
    }

    private void makeTrackArray() {
        for(Track track : trackObjectsArray) {
            trackArray.add(track.getSongName() + " - " + track.getArtist());
        }
    }


    public void makeTrackAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, trackArray);
        lvItems.setAdapter(arrayAdapter);
    }
}
