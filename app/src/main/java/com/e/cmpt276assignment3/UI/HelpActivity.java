package com.e.cmpt276assignment3.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.cmpt276assignment3.R;
/*
Displays instructions to play, and contains hyperlinks to other resources and cmpt276 website.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Button backtomain=(Button) findViewById(R.id.backmenu);
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //Returning Necessary Activity
    public static Intent makeIntent(Context context) {
        Intent helpintent = new Intent(context, HelpActivity.class);
        return helpintent;
    }



}