package com.pje.def.wikibook.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.pje.def.wikibook.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Activity which use the camera
 */
public class FormationCameraActivity extends Activity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView surfaceCamera;
    private Boolean isPreview;
    public static final String DATA_PICTURE_TAKEN = "data_picture";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Nous mettons l'application en plein écran et sans barre de titre
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isPreview = false;

        // Nous appliquons notre layout
        setContentView(R.layout.activity_formation_camera);

        // Nous récupérons notre surface pour le preview
        surfaceCamera = (SurfaceView) findViewById(R.id.surfaceViewCamera);
        // Quand nous cliquons sur notre surface
        surfaceCamera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Nous prenons une photo
                if (camera != null) {
                    SavePicture();
                }

            }
        });
        // Méthode d'initialisation de la caméra
        InitializeCamera();
    }
    private void SavePicture() {
        try {
                    // Callback pour la prise de photo
            Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

                public void onPictureTaken(byte[] data, Camera camera) {
                    if (data != null) {
                        Toast.makeText(getApplicationContext(),"Picture Taken", Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(DATA_PICTURE_TAKEN, data);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                }
            };
            camera.takePicture(null, null, pictureCallback);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * Initialise camera
     */
    public void InitializeCamera() {
    // Nous attachons nos retours du holder à notre activité
        surfaceCamera.getHolder().addCallback(this);
    // Nous spécifiions le type du holder en mode SURFACE_TYPE_PUSH_BUFFERS
        surfaceCamera.getHolder().setType(
                SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Nous arrêtons la camera et nous rendons la main
        if (camera != null) {
            camera.stopPreview();
            isPreview = false;
            camera.release();
        }
    }
    public void surfaceCreated(SurfaceHolder holder) {
        // Nous prenons le contrôle de la camera
        if (camera == null) {
            camera = Camera.open();
        }
    }

    // Quand la surface change
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

        // Si le mode preview est lancé alors nous le stoppons
        if (isPreview) {
            camera.stopPreview();
        }
        // Nous récupérons les paramètres de la caméra
        Camera.Parameters parameters = camera.getParameters();

        // Nous changeons la taille
        //parameters.setPreviewSize(width, height);

        // Nous appliquons nos nouveaux paramètres
        camera.setParameters(parameters);

        try {
            // Nous attachons notre prévisualisation de la caméra au holder de la
            // surface
            camera.setPreviewDisplay(surfaceCamera.getHolder());
        } catch (IOException e) {
        }

        // Nous lançons la preview
        camera.startPreview();

        isPreview = true;
    }
    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    // Mise en pause de l'application
    @Override
    public void onPause() {
        super.onPause();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formation_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
