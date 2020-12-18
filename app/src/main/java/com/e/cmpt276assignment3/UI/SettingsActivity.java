package com.e.cmpt276assignment3.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.e.cmpt276assignment3.R;
import com.e.cmpt276assignment3.model.BombField;

public class SettingsActivity extends AppCompatActivity {
    BombField mainfield=BombField.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getscoredata();
        scoreui();
        createbombfieldbuttons();
        createnumberofbombsbutton();
        Button erasebtn= (Button) findViewById(R.id.erasetimes);
        erasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settimesplayedtonil();
            }
        });

        Button erasescr= (Button) findViewById(R.id.erasescores);
        erasescr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setscorestonil();
                scoreui();
            }
        });
    }

    //Used to update scores
    private void scoreui() {
        TextView score=(TextView)(findViewById(R.id.topscore1));
        TextView score2=(TextView)(findViewById(R.id.topscore2));
        TextView score3=(TextView)(findViewById(R.id.topscore3));
        score.setText(getString((R.string.topscore1),mainfield.getTopscoremode1()));
        score2.setText(getString((R.string.topscore2),mainfield.getTopscoremode2()));
        score3.setText(getString((R.string.topscore3),mainfield.getTopscoremode3()));
    }

    //Following class sets all top scores to 0.
    private void setscorestonil() {
        mainfield.setTopscoremode1(0);
        mainfield.setTopscoremode2(0);
        mainfield.setTopscoremode3(0);

        //for score mode 1
        SharedPreferences scoremode1 = this.getSharedPreferences("scoremode1", MODE_PRIVATE);
        SharedPreferences.Editor editor = scoremode1.edit();
        editor.putInt("scoremodeone", mainfield.getTopscoremode1());
        editor.apply();

        // for score mode 2
        SharedPreferences scoremode2 = this.getSharedPreferences("scoremode2", MODE_PRIVATE);
        editor = scoremode2.edit();
        editor.putInt("scoremodetwo", mainfield.getTopscoremode2());
        editor.apply();

        //for score mode 3
        SharedPreferences scoremode3 = this.getSharedPreferences("scoremode3", MODE_PRIVATE);
        editor = scoremode3.edit();
        editor.putInt("scoremodethree", mainfield.getTopscoremode3());
        editor.apply();
    }

    //Reusing the preferences to make the times played to 0 again.
    private void settimesplayedtonil() {
        mainfield.setTimesplayed(0);
        SharedPreferences timesplayedpref = this.getSharedPreferences("playtime", MODE_PRIVATE);
        SharedPreferences.Editor editor = timesplayedpref.edit();
        editor.putInt("playtimeint", mainfield.getTimesplayed());
        editor.apply();
    }


    //to get score data from prefs
    private void getscoredata() {
        mainfield.setTopscoremode1(GameActivity.getscoremode1(this));
        mainfield.setTopscoremode2(GameActivity.getscoremode2(this));
        mainfield.setTopscoremode3(GameActivity.getscoremode3(this));

    }

    //FOllowing classes store the preferences for use in other activities
    private void savenewcol(int newcolno) {
        SharedPreferences colprefs = this.getSharedPreferences("COLPREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = colprefs.edit();
        editor.putInt("COLNO", newcolno);
        editor.apply();
    }
    private void savenewrow(int newrowno) {
        SharedPreferences rowprefs = this.getSharedPreferences("ROWPREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = rowprefs.edit();
        editor.putInt("ROWNO",newrowno);
        editor.apply();
    }


    private void savenewbomb(int bombno) {
        SharedPreferences bombprefs = this.getSharedPreferences("BOMBPREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = bombprefs.edit();
        editor.putInt("BOMBNUM", bombno);
        editor.apply();
    }

    public static int getRowno(Context context){
        int startrow = context.getResources().getInteger(R.integer.defaultrow);
        SharedPreferences prefs = context.getSharedPreferences("ROWPREFS", MODE_PRIVATE);
        int retrow = prefs.getInt("ROWNO",startrow);
        return retrow;
    }

    public static int getColno(Context context){
        int defcol= context.getResources().getInteger(R.integer.defaultcol);
        SharedPreferences colprefs = context.getSharedPreferences("COLPREFS", MODE_PRIVATE);
        int retcol = colprefs.getInt("COLNO",defcol);
        return retcol;
    }

    public static int getBombno(Context context){
        int defbomb = context.getResources().getInteger(R.integer.defaultbomb);
        SharedPreferences bombprefs = context.getSharedPreferences("BOMBPREFS", MODE_PRIVATE);
        return bombprefs.getInt("BOMBNUM",defbomb);
    }

    /*
     //The settings menu creates two spinners which has a list of options on mazes, and bomb number to choose from.
     They also have their default settings to keep 4x6, and 4 bombs as the default settings
     */

    private void createbombfieldbuttons() {
        Spinner bombfieldspinner= (Spinner) findViewById(R.id.fieldspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.num_fieldoptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bombfieldspinner.setAdapter(adapter);

        bombfieldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                { savenewrow(4);
                  savenewcol(6); }
                if(position==1)
                { savenewrow(4);
                savenewcol(6); }
                if(position==2)
                { savenewrow(5);
                savenewcol(10); }
                if(position==3)
                { savenewrow(6);
                savenewcol(15); }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                savenewrow(4);
                savenewcol(6);
            }
        });

      }



    private void createnumberofbombsbutton() {
        Spinner numbombspinner= (Spinner) findViewById(R.id.bombsnum);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbomb, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numbombspinner.setAdapter(adapter);

        numbombspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                { savenewbomb(4);
                }
                if(position==1)
                { savenewbomb(4);
                }
                if(position==2)
                { savenewbomb(8);
                }
                if(position==3)
                { savenewbomb(12);
                }
                if(position==4)
                { savenewbomb(15);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                savenewbomb(6);
            }
        });

    }



    public static Intent makeIntent(Context context) {
        Intent settingsintent = new Intent(context, SettingsActivity.class);
        return settingsintent;
    }
}