package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TrackListActivity extends Activity {

    ListView lvTracks;
    ArrayList<Track> trackList;
    ArrayList<String> arrayStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        lvTracks = findViewById(R.id.lvTracks);

        loadFromSharedPrefs();
        makeStringList();
        makeTrackAdapter();

        lvTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), TrackViewActivity.class);
                intent.putExtra("track", trackList.get(i));
                startActivity(intent);
            }
        });
    }

    private void makeStringList() {
        arrayStringList = new ArrayList<String>();
        for(Track item : trackList) {
            arrayStringList.add(item.getSongName() + " - " + item.getArtist());
        }
    }

    private void loadFromSharedPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String jsonList = prefs.getString("tracks", "");

        Type type = new TypeToken<List<Track>>(){}.getType();
        trackList = gson.fromJson(jsonList,type);
    }

    public void makeTrackAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayStringList);
        lvTracks.setAdapter(arrayAdapter);
    }
}