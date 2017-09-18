package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sebastiaan on 18-Sep-17.
 */

public class HttpRequestHelper {

    protected static synchronized String downloadFromServer(String... params) {
        String result = "";
        String chosenTag = params[0];

        URL url = null;
        String APIkey = "320ef4da2162e681bff239ba0d904786";

        try {
            //url = new URL("http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + chosenTag + "&api_key="+ APIkey + "&format=json");
            url = new URL("http://ws.audioscrobbler.com/2.0/?method=track.search&track=halo&api_key=320ef4da2162e681bff239ba0d904786&format=json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

         HttpURLConnection connect;
        if(url != null) {
            try {
                //connect = (HttpURLConnection) url.openConnection();
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                Integer responseCode = connect.getResponseCode();
                if(responseCode >= 200 && responseCode < 300) {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;

                    while((line = bReader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
