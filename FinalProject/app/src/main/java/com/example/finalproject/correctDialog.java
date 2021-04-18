package com.example.finalproject;

// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class correctDialog extends DialogFragment {

    public interface correctDialogListener {
        // Callback for "OK" button
        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }

    UserDatabase mUserDb;
    TextView text;
    correctDialogListener listener;
    String textViewDialog;
    boolean correct;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public correctDialog(String dialog, boolean correctInput) {
        textViewDialog = dialog;
        correct = correctInput;
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
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.generic_dialog_fragment, (ViewGroup) getView(), false);
        mUserDb = UserDatabase.getInstance(getActivity().getApplicationContext());
        prefs = getContext().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        editor = prefs.edit();
        if (correct) {
            builder.setTitle("Correct!");
        }
        else {
            builder.setTitle("Game Over.");
            User user = new User(prefs.getString("username", "ERROR"), prefs.getInt("currentScore", -1));
            mUserDb.userDao().insertUser(user);
        }
        text = inflater.findViewById(R.id.text);
        text.setText(textViewDialog);

        // Creating the "OK" and "Default" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick();
                        if(!correct) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick();
                        if(!correct) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
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
        listener = (correctDialogListener) context;
    }
}
