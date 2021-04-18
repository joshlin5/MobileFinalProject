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

public class SameUsernameFragment extends DialogFragment{

    public interface SameUsernameFragmentListener {
        // Callback for "OK" button
        void onDialogPositiveClick();
        void onSameDialogNegativeClick();
    }

    // Callback listener
    SameUsernameFragmentListener listener;
    TextView sameUsername;
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
        // Telling Dialog which layout to use and to use the EditText
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.use_same_username_fragment, (ViewGroup) getView(), false);
        // Initializing
        sameUsername = inflater.findViewById(R.id.sameUsername);
        prefs = this.requireActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        // Setting text for TextView
        String username = prefs.getString("username", null);
        sameUsername.setText("Do you want to use the same username " + username + "?");


        // Creating the "OK" and "Default" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Reset current points to 0
                        listener.onDialogPositiveClick();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onSameDialogNegativeClick();
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
        listener = (SameUsernameFragmentListener) context;
    }
}
