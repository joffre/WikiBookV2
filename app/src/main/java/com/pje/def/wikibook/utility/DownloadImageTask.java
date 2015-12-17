package com.pje.def.wikibook.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Task who load a pic from an URL, save it and set pic to an ImageView
 * Created by Geoffrey on 16/12/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Bitmap image;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
        this.image = null;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        image = result;
        bmImage.setImageBitmap(result);
    }

    /**
     * Return pic after loading
     * @return
     */
    public Bitmap getImage(){
        return this.image;
    }
}
