package com.pje.def.wikibook.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Geoffrey on 16/12/2015.
 */
public class ImageCollection {

    private static final String IMAGE_FOLDER = "covers/";
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
        return null;
    }

    public static boolean exist(String isbn){
        String imagePath = IMAGE_FOLDER + isbn + IMAGE_FORMAT;
        return new File(imagePath).exists();
    }

    public static boolean addImage(String isbn, Bitmap image){
        if(image == null) return false;
        File file = new File(getPath(isbn));
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            return true;
        } catch (FileNotFoundException e){
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
        return IMAGE_FOLDER + isbn + IMAGE_FORMAT;
    }
}
