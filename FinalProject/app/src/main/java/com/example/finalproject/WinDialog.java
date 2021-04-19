package com.example.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class WinDialog extends DialogFragment {
    public interface WinDialogListener {
        // Callback for "OK" button
        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }
    // Database
    UserDatabase mUserDb;
    // TextView for explanation of correct answer
    TextView text;
    // Callback listener
    CorrectDialog.correctDialogListener listener;
    // Text to display
    String textViewDialog;
    // Shared pref file and editor
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    // Constructor, takes in a dialog to be displayed
    public WinDialog(String dialog) {
        textViewDialog = dialog;

    }

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
        // Telling Dialog which layout to use and to use the EditText
        // Initializing
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.generic_dialog_fragment, (ViewGroup) getView(), false);
        mUserDb = UserDatabase.getInstance(getActivity().getApplicationContext());
        prefs = getContext().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        editor = prefs.edit();

        // Sets the title to either correct or game over depending on boolean correct

        builder.setTitle("Finished!");
        User user = new User(prefs.getString("username", "ERROR"), prefs.getInt("currentScore", -1));
        mUserDb.userDao().insertUser(user);
        // Display dialog given in constructor
        text = inflater.findViewById(R.id.text);
        text.setText(textViewDialog);
        // Creating the "OK" and "Default" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Callback for activity to know what was clicked in dialog
                        listener.onDialogPositiveClick();
                        // If player choose the wrong answer, restart game at the starting screen
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Callback for activity to know what was clicked in dialog
                        listener.onDialogNegativeClick();
                        // If player choose the wrong answer, restart game at the starting screen
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

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
        listener = (CorrectDialog.correctDialogListener) context;
    }

}
