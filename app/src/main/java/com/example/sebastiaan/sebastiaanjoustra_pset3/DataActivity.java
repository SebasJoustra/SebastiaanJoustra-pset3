package com.example.sebastiaan.sebastiaanjoustra_pset3;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        //tvResult = (TextView) findViewById(R.id.tvResult);
        lvItems = (ListView) findViewById(R.id.lvItems);
        assert lvItems != null;

        Bundle extras = getIntent().getExtras();
        tracksString = extras.getString("data");
        makeTrackArray();
        makeTrackAdapter();


    }

    private void makeTrackArray() {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(tracksString);
            Log.d(TAG, String.valueOf(jsonArray.length()));
            JSONObject testObj = jsonArray.getJSONObject(4);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject trackObj = jsonArray.getJSONObject(i);
                String songName = trackObj.getString("name");
                String artistName = trackObj.getString("artist");
                System.out.println(songName);
                trackArray.add(songName + " - " + artistName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void makeTrackAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, trackArray);
        lvItems.setAdapter(arrayAdapter);
    }
}
