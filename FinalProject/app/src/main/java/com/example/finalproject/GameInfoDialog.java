package com.example.finalproject;

// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class GameInfoDialog extends DialogFragment{

    public interface GameInfoDialogListener {
        // Callback for "OK" button
        void onDialogPositiveClick();
    }

    // Callback listener
    GameInfoDialogListener listener;
    EditText username;
    TextView usernameTextView;
    // Username of player
    String name;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    /**
     *
     * @param savedInstanceState State of application to save
     * @pre
     * savedInstanceState = NULL or savedInstanceState = [some data]
     * @post
     * [dialog will be created using target_points]
     * [dialog will have a "OK" and "Default" button]
     */
    // Inspired from Zybooks and Android Developer documentation
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Making a new dialog using a fragment from target_points
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Game Instructions");
        // Telling Dialog which layout to use
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.username_fragment, (ViewGroup) getView(), false);
        // Initializing
        username = inflater.findViewById(R.id.usernameEditText);
        usernameTextView = inflater.findViewById(R.id.usernameTextView);
        prefs = this.requireActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        editor = prefs.edit();


        // Creating the "OK" and "Default" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Get name typed
                        name = username.getText().toString();
                        // If they typed a name
                        if (!name.equals("") && name.length() > 0) {
                            // Add name to shared preference file
                            editor.putString("username", name);
                        }
                        else {
                            // If no name was typed then default to Bob
                            editor.putString("username", "Bob");
                        }
                        // Callback for Ok button
                        listener.onDialogPositiveClick();
                        // Applying changes to shared pref file
                        editor.apply();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nothing happens and goes back to activity that called it
                    }
                });
        return builder.create();
    }

    /**
     *
     * @param context Interface to global information about an application environment
     * @pre
     * context = NULL or context = [some data]
     * @post
     * [listener will be attached to context]
     */
    // Inspired from Zybooks
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (GameInfoDialogListener) context;
    }
}
