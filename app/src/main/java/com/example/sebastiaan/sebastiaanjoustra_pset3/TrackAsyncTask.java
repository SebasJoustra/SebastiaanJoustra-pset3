package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Context;
import android.os.AsyncTask;
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
        //ArrayList<String> data = new ArrayList<String>();
        try {
            JSONObject trackStreamObj = new JSONObject(result);
            JSONObject resultsObj = trackStreamObj.getJSONObject("results");
            JSONObject trackMatchesObj = resultsObj.getJSONObject("trackmatches");
            JSONArray tracksArray = trackMatchesObj.getJSONArray("track");
            data = tracksArray.toString();
            //System.out.println(tracksArray.toString());

//            for(int i=0; i<tracksArray.length(); i++) {
//                tracksArray.getJSONObject(i);
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mainAct.trackStartIntent(data);
    }
}
