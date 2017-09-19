package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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
                    trackSearch();
                }
                return false;
            }
        });
    }

    public void trackSearch() {
        String trackSearch = etTrackName.getText().toString();
        TrackAsyncTask asyncTask = new TrackAsyncTask(this);
        asyncTask.execute(trackSearch);

        etTrackName.getText().clear();
    }

    public void trackStartIntent(Track[] trackData) {
        Intent dataIntent = new Intent(this, DataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tracks", trackData);
        dataIntent.putExtras(bundle);
        this.startActivity(dataIntent);
    }
}
