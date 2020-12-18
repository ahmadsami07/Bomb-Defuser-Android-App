package com.e.cmpt276assignment3.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.e.cmpt276assignment3.R;
/*
Following class gives the winning message and eliminates the activity
 */
public class WinMessageFragment extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //create the view to show
        View V= LayoutInflater.from(getActivity()).inflate(R.layout.message_layout,null);
        //create a button listener
        DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        };
        //build the alert dialogr
        return new AlertDialog.Builder(getActivity()).setTitle("CONGRATULATIONS, YOU'RE A TRUE HERO.").setView(V).setPositiveButton("Back To Main Menu",listener).create();
    }
}
