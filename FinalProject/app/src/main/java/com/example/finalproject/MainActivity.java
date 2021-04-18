package com.example.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GameInfoDialog.GameInfoDialogListener, SameUsernameFragment.SameUsernameFragmentListener{

    // Database
    private UserDatabase mUserDb;
    private final int REQUEST_TAKE_PHOTO = 1;
    private Menu mMenu;
    // Start button
    Button startButton;
    private ImageView mPhoto;
    private File mPhotoFile;
    // Shared pref file and editor
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    // Example of how to get the username from pref file
    // SharedPreferences prefs = this.getActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
    // prefs.getString("username", "ERROR");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing shared pref file and editor
        prefs = this.getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        editor = prefs.edit();

        mPhoto = findViewById(R.id.background);
        // Initializing start button and setting onClick listener
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            String name = prefs.getString("username", null);

            if(name != null) {
                SameUsernameFragment dialog = new SameUsernameFragment();
                dialog.show(getSupportFragmentManager(), "Username Game Info Dialog");
            }
            else {
                GameInfoDialog dialog = new GameInfoDialog();
                dialog.show(getSupportFragmentManager(), "Username Game Info Dialog");
            }
        });
        mUserDb = UserDatabase.getInstance(getApplicationContext());
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, Weather.class);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick() {
        SharedPreferences prefs = this.getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String username = prefs.getString("username", "ERROR");

        int count = mUserDb.userDao().userCount(username);
        int highScore;
        if (count > 0) {
            highScore = mUserDb.userDao().highScore(username);
            editor.putInt("highScore", highScore);
        }
        else {
            editor.putInt("highScore", 0);
        }
        editor.putInt("currentScore", 0);
        editor.apply();

        Intent intent = new Intent(this, Hallway.class);
        intent.putExtra("previousActivity", "main");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Photo:
                takePhotoClick();
                return true;

            case R.id.weather:

                Intent intent = new Intent(this, Weather.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void takePhotoClick() {

        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoCaptureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            try {
                mPhotoFile = createImageFile();
            }
            catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }

            // If the File was successfully created, start camera app
            if (mPhotoFile != null) {

                // Create content URI to grant camera app write permission to photoFile
                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.example.finalproject",
                        mPhotoFile);

                // Add content URI to intent
                photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                // Start camera app
                startActivityForResult(photoCaptureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            displayPhoto();


           // mSaveButton.setEnabled(true);
        }
    }

    private File createImageFile() throws IOException {
        // Create a unique image filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFilename = "photo_" + timeStamp + ".jpg";

        // Get file path where the app can save a private image
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, imageFilename);
    }

    private void displayPhoto() {
        // Get ImageView dimensions
        int targetW = mPhoto.getWidth();
        int targetH = mPhoto.getHeight();

        // Get bitmap dimensions
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a smaller bitmap that fills the ImageView
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath(), bmOptions);

        // Display smaller bitmap
        mPhoto.setImageBitmap(bitmap);

    }

    @Override
    public void onSameDialogNegativeClick() {
        GameInfoDialog dialog = new GameInfoDialog();
        dialog.show(getSupportFragmentManager(), "Username Game Info Dialog");
    }
}