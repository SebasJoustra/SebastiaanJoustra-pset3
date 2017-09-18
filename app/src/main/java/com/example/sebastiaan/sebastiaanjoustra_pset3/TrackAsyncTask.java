package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sebastiaan on 18-Sep-17.
 */

public class TrackAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    MainActivity mainAct;

    public TrackAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Searching for tracks...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String data = "";
        Track[] trackArray = null;

        try {
            JSONObject trackStreamObj = new JSONObject(result);
            JSONObject resultsObj = trackStreamObj.getJSONObject("results");
            JSONObject trackMatchesObj = resultsObj.getJSONObject("trackmatches");
            JSONArray tracksArray = trackMatchesObj.getJSONArray("track");
            data = tracksArray.toString();

            trackArray = makeTrackObject(tracksArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mainAct.trackStartIntent(trackArray);
    }

    private Track[] makeTrackObject(JSONArray tracksArray) {
        Track[] trackArray = new Track[tracksArray.length()];

        try {
            for (int i = 0; i < tracksArray.length(); i++) {
                JSONObject trackObj = tracksArray.getJSONObject(i);
                String songName = trackObj.getString("name");
                String artistName = trackObj.getString("artist");
                String lastFMUrl = trackObj.getString("url");
                JSONArray imagesArray = trackObj.getJSONArray("image");
                JSONObject imageObj = imagesArray.getJSONObject(2);
                String imageUrl = imageObj.getString("#text");
                trackArray[i] = new Track(songName, artistName, imageUrl, lastFMUrl);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return trackArray;
    }
}
