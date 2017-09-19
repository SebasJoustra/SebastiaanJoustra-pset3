package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTrackName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTrackName = (EditText) findViewById(R.id.etTrackName);
        assert etTrackName != null;

        etTrackName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                System.out.println(keyCode);
                System.out.println(keyEvent);
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    trackSearch((View) etTrackName);
                }
                return false;
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:

                    return true;
                case R.id.nav_list:
                    Intent intentToList = new Intent(getApplicationContext(), TrackListActivity.class);
                    startActivity(intentToList);
                    finish();
                    return true;
            }
            return false;
        }

    };

    public void trackSearch(View view) {
        String trackSearch = etTrackName.getText().toString();
        if(trackSearch.matches("")) {
            Toast.makeText(this, "Please give an input...", Toast.LENGTH_SHORT).show();
        } else {
            TrackAsyncTask asyncTask = new TrackAsyncTask(this);
            asyncTask.execute(trackSearch);

            etTrackName.getText().clear();
        }
    }

    public void trackStartIntent(Track[] trackData) {
        if(trackData != null) {
            Intent dataIntent = new Intent(this, DataActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("tracks", trackData);
            dataIntent.putExtras(bundle);
            this.startActivity(dataIntent);
        } else {
            Toast.makeText(this, "Did not receive information from server...", Toast.LENGTH_LONG).show();
        }
    }

    private void makeSharedPrefs() {
        ArrayList<Track> newArray = new ArrayList<Track>();
        newArray.add(new Track("a", "b", "c", "d"));



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String jsonList = gson.toJson(newArray);

        editor.putString("tracks", jsonList);
        editor.apply();

    }


}
