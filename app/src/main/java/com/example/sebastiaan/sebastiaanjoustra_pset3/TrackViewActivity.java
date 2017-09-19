package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrackViewActivity extends AppCompatActivity {

    TextView tvTrackName;
    TextView tvArtistName;
    TextView btUrl;
    Button btAddToList;
    boolean inList = false;
    int inListId;

    Track track;
    List<Track> trackList;
    String jsonList;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view);

        Intent previousIntent = getIntent();
        //String trackJSON = previousIntent.getStringExtra("trackInfo");
        track = (Track) previousIntent.getSerializableExtra("track");

        tvTrackName = (TextView) findViewById(R.id.tvTrackName);
        tvArtistName = (TextView) findViewById(R.id.tvArtistName);
        btUrl = (Button) findViewById(R.id.btUrl);
        btAddToList = (Button) findViewById(R.id.btAddToTrackList);

        loadSharedPrefs();
        checkSongInList();
        setComponents();

    }



    private void loadSharedPrefs() {
        // get shared prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        jsonList = prefs.getString("tracks", "");


        // if list doesn't exist yet, create one. Otherwise load the existing one
        if(jsonList.equals("")) {
            trackList = new ArrayList<Track>();
        } else {
            Type type = new TypeToken<List<Track>>(){}.getType();
            trackList = gson.fromJson(jsonList, type);
        }
    }

    private void setComponents() {
        tvTrackName.setText(track.getSongName());
        tvArtistName.setText(track.getArtist());

        new DownloadImageTask((ImageView) findViewById(R.id.ivTrack)).execute(track.getImageLink());

        btUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getLastFMLink()));
                startActivity(browserIntent);
            }
        });

        if(inList) {
            btAddToList.setText(R.string.remove_from_list_bt);
            btAddToList.setBackgroundColor(Color.RED);
        }

    }

    private void checkSongInList() {
        for(int i=0; i<trackList.size(); i++) {
            if(track.getSongName().equals(trackList.get(i).getSongName()) &&
                    track.getArtist().equals(trackList.get(i).getArtist())) {
                inList = true;
                inListId = i;
            }
        }
    }

    public void saveToSharedPrefs(View view) {
        Gson gson = new Gson();
        if(!inList) {
            trackList.add(track);

        } else {
            trackList.remove(inListId);
        }

        SharedPreferences.Editor editor = prefs.edit();
        jsonList = gson.toJson(trackList);

        editor.putString("tracks", jsonList);
        editor.apply();

        Intent intent = new Intent(this, TrackListActivity.class);
        this.startActivity(intent);

        finish();

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        // Source: https://stackoverflow.com/questions/5776851/load-image-from-url
        ImageView ivTrackImage;

        public DownloadImageTask(ImageView image) {
            this.ivTrackImage = image;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmpResult = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmpResult = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmpResult;
        }

        protected void onPostExecute(Bitmap result) {
            ivTrackImage.setImageBitmap(result);
        }
    }
}


