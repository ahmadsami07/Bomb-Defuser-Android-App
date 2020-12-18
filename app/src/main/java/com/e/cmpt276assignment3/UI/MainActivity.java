package com.e.cmpt276assignment3.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.e.cmpt276assignment3.R;

/*
Activity which is the launchpad on which main menu is launched
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Following activity returns the main menu's intent.
        Intent mainmenuintent=MainMenu.makeIntent(MainActivity.this);
        startActivity(mainmenuintent);
        finish();



    }
//For splash screen
    public static Intent makeIntent(Context context) {
        Intent mainactivityintent = new Intent(context, MainActivity.class);
        return mainactivityintent;
    }
}