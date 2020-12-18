package com.e.cmpt276assignment3.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.cmpt276assignment3.R;
/*
Will contain the buttons to guide through the whole range of activities.
 */

public class MainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //for help button
        Button helpbtn=(Button)findViewById(R.id.helpbutton);
        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Following activity returns the help menu's intent
                Intent helpintent=HelpActivity.makeIntent(MainMenu.this);
                startActivity(helpintent);
            }
        });

        //for help button
        Button defusbtn=(Button)findViewById(R.id.defuse);
        defusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Following activity returns the help menu's intent
                Intent gameintent=GameActivity.makeIntent(MainMenu.this);
                startActivity(gameintent);
            }
        });

        Button settbtn=(Button)findViewById(R.id.settingsbtn);
        settbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Following activity returns the help menu's intent
                Intent settintent=SettingsActivity.makeIntent(MainMenu.this);
                startActivity(settintent);
            }
        });

    }

    //Returning Necessary Activity
    public static Intent makeIntent(Context context) {
        Intent menuintent = new Intent(context, MainMenu.class);
        return menuintent;
    }





}