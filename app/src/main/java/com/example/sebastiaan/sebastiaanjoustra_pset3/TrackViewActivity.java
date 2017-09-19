package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class TrackViewActivity extends AppCompatActivity {

    TextView tvTrackName;
    TextView tvArtistName;
    TextView btUrl;

    Track track;

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
        setComponents();

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
//
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


