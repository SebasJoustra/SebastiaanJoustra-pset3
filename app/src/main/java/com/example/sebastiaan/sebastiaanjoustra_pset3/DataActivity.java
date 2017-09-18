package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class DataActivity extends AppCompatActivity {
    private static final String TAG = DataActivity.class.getSimpleName();

    TextView tvResult;
    ListView lvItems;
    String tracksString;
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
