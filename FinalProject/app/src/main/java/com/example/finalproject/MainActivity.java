package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    Button startButton, photo, weather, about;
    private ImageView mPhoto;
    private File mPhotoFile;
    // Shared pref file and editor
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

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
        photo = findViewById(R.id.take_photo);
        weather = findViewById(R.id.WeatherB);
        about = findViewById(R.id.aboutButton);
        startButton.setOnClickListener(v -> {
            // Username from shared pref file
            String name = prefs.getString("username", null);

            // Check whether username is already in the pref file
            if(name != null) {
                // Dialog asking user if they want to use the same username again
                SameUsernameFragment dialog = new SameUsernameFragment();
                dialog.show(getSupportFragmentManager(), "Username Game Info Dialog");
            }
            else {
                // Dialog asking user for their username
                GameInfoDialog dialog = new GameInfoDialog();
                dialog.show(getSupportFragmentManager(), "Username Game Info Dialog");
            }
        });
        about.setOnClickListener(v -> {
            //Start activity to get location and weather to update view
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        });
        weather.setOnClickListener(v -> {
            //Start activity to get location and weather to update view
            Intent intent = new Intent(this, Weather.class);
            startActivityForResult(intent,2);
        });
        photo.setOnClickListener(v -> {
            //Start camera app to take photo
            takePhotoClick();
        });
        // Initializing database
        mUserDb = UserDatabase.getInstance(getApplicationContext());
        User user = new User("Bob", 10);
        mUserDb.userDao().insertUser(user);
    }

    // For both GameInfoDialog and SameUsernameFragment callback
    @Override
    public void onDialogPositiveClick() {
        // Get it again in case it changed since dialog popped up
        String username = prefs.getString("username", "ERROR");
        // Check whether user is already in database or not
        int count = mUserDb.userDao().userCount(username);
        int highScore;

        if (count > 0) {
            // If username is already in database, find the highest score
            highScore = mUserDb.userDao().highScore(username);
            editor.putInt("highScore", highScore);
        }
        else {
            // Otherwise no previous score so set as 0
            editor.putInt("highScore", 0);
        }
        // Current Score always starts at 0 at the start of a game
        editor.putInt("currentScore", 0);
        editor.apply();

        // Start Hallway.class
        Intent intent = new Intent(this, Hallway.class);
        intent.putExtra("previousActivity", "main");
        startActivity(intent);
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
                        "com.example.finalproject.fileprovider",
                        mPhotoFile);

                // Add content URI to intent
                photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                // Start camera app
                startActivityForResult(photoCaptureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }
    //Will handle activities that are returning data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            displayPhoto();

        }
        //RequestCode: 2 is for the weather activity
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String resource = data.getStringExtra("resource");
                if(resource.equals("clear sky"))
                    mPhoto.setImageResource(R.drawable.school);

                else if(resource.contains("rain"))
                    mPhoto.setImageResource(R.drawable.rain);
                else if(resource.contains("cloud"))
                    mPhoto.setImageResource(R.drawable.schoolcloudy);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("TAG", "Fail");
            }
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

    // SameUsernameFragment callback
    @Override
    public void onSameDialogNegativeClick() {
        // If they don't want to use the same username again
        // Starts GameInfoDialog for setting new username
        GameInfoDialog dialog = new GameInfoDialog();
        dialog.show(getSupportFragmentManager(), "Username Game Info Dialog");
    }
}