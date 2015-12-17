package com.pje.def.wikibook.bdd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Geoffrey on 16/12/2015.
 */
public class ImageCollection {

    private static final String IMAGE_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+"covers/";
    private static final String IMAGE_FORMAT = ".jpg";

    public static void init(){
        File directory = new File(IMAGE_FOLDER);
        if(!directory.exists()){
            directory.mkdir();
        }
    }

    public static Bitmap getImage(String isbn){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(getPath(isbn), options);
        return bitmap;
    }

    public static boolean exist(String isbn){
        return new File(getPath(isbn)).exists();
    }

    public static boolean addImage(String isbn, Bitmap image){
        if(image == null) return false;
        File file = new File(getPath(isbn));
        try {
            if(!file.exists()) file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            return true;
        } catch (IOException e){
            System.out.println("AddImage " + e.getMessage());
            return false;
        }
    }

    public static void deleteImage(String isbn){
        File file = new File(getPath(isbn));
        if(file.exists()){
            file.delete();
        }
    }

    private static String getPath(String isbn){
        System.out.println(IMAGE_FOLDER+ isbn + IMAGE_FORMAT);
        return IMAGE_FOLDER+ isbn + IMAGE_FORMAT;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
