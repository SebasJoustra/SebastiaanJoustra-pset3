package com.example.sebastiaan.sebastiaanjoustra_pset3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Sebastiaan on 20-Sep-17.
 */

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    // Source: https://stackoverflow.com/questions/5776851/load-image-from-url
    ImageView ivTrackImage;

    public ImageAsyncTask(ImageView image) {
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
