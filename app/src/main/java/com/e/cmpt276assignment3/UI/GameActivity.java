package com.e.cmpt276assignment3.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.e.cmpt276assignment3.R;
import com.e.cmpt276assignment3.model.BombField;
/*
Heart of the game, populates the buttons with bombs, checks button on-check with the underlying BombField.
 */

public class GameActivity extends AppCompatActivity {


    BombField mainfield=BombField.getInstance();
    Button testbutton[][];
    private boolean scanagain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getprefs();
        initialui();
        populateButtons();
    }

    //Returning Necessary Activity
    public static Intent makeIntent(Context context) {
        Intent gameintent = new Intent(context, GameActivity.class);
        return gameintent;
    }
    //When activity destroyed by winfragment, we will reset all scannum and defuseno
    //in the background to 0
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainfield.setScannum(0);
        mainfield.setDefuseno(0);
    }

    //The initial ui will display the top scores, scans used and defused and times played, accessing the data from the shared preferences
    private void initialui() {
        TextView defuser=(TextView)(findViewById(R.id.bombsfound));
        TextView scanner=(TextView)(findViewById(R.id.Scansused));
        TextView playtime=(TextView)(findViewById(R.id.timesplayed));
        TextView score=(TextView)(findViewById(R.id.gametopscore));

        defuser.setText(getString((R.string.Defused),mainfield.getDefuseno(),mainfield.getBombno()));
        scanner.setText(getString((R.string.Scans),mainfield.getScannum()));
        playtime.setText(getString((R.string.timesplayed),mainfield.getTimesplayed()));
        if(mainfield.getRowno()==4)
        { score.setText(getString((R.string.score),getscoremode1(this))); }
        if(mainfield.getRowno()==5)
        { score.setText(getString((R.string.score),getscoremode2(this))); }
        if(mainfield.getRowno()==6)
        { score.setText(getString((R.string.score),getscoremode3(this))); }
    }

    //Getting the pref data
    private void getprefs() {

        mainfield.setRowno(SettingsActivity.getRowno(this));
        mainfield.setColno(SettingsActivity.getColno(this));
        mainfield.setBombno(SettingsActivity.getBombno(this));
        mainfield.setTimesplayed(gettimesplayedpref(this));
    }

    //Populating field with buttons, updating UI as we go
    private void populateButtons() {
        final boolean[] bombdef = {false};
        TableLayout table = (TableLayout) findViewById(R.id.tableforbuttons);
       // System.out.println(mainfield.getRowno());
        testbutton = new Button[mainfield.getRowno()][mainfield.getColno()];
        mainfield.bombfieldmaker();
        for (int row = 0; row < mainfield.getRowno(); row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            table.addView(tableRow);
            for (int col = 0;col< mainfield.getColno(); col++) {
                final int fincol = col;
                final int finrow = row;
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lockButtonSizes();
                        if ((mainfield.if_bomb_found(finrow, fincol))) {
                            bombfound(finrow, fincol);
                            bombupdatescan(finrow,fincol);
                        } else {
                            wrong_buzzersound();
                            button.setText(getString(R.string.scantimeres, mainfield.scanbombs(finrow, fincol)));
                        }
                        //updating the UI right after button clicked
                        finalui();
                        //Checking if player has won the game
                        wincheck();
                    }
                });

                tableRow.addView(button);
                testbutton[row][col] = button;
                }
            }

        }

        //Checking rows nad columns if a bomb is found to
    //decrement scan counter
    private void bombupdatescan(int finrow, int fincol) {
        Button bombbutton = testbutton[finrow][fincol];
        for(int col=0;col<mainfield.getColno();col++)
        {
            //this iterates over the increasing rows while keeping a constant column
            //where bomb is in
            //The fading animation allows the corresponding surrounding fields to fade in and out,
            //creating a pulse wave
            //Only for buttons whose scan numbers are revealed(bomb or no bomb)
            if (mainfield.getval(finrow, col) == 3 || mainfield.getval(finrow, col) == 4)
            {
                bombbutton = testbutton[finrow][col];
                Animation fade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadinng);
                bombbutton.setAnimation(fade);
                bombbutton.setText(getString(R.string.scantimeres, mainfield.scanbombs(finrow, col)));
                //System.out.println("bombing1"+getString(R.string.scantimeres, mainfield.scanbombs(finrow, col)));
                testbutton[finrow][col] = bombbutton;
            }
        }
        for(int row=0;row<mainfield.getRowno();row++)
        {
            //this iterates over the increasing rows while keeping a constant row
            //where bomb is in
            //The fading animation allows the corresponding surrounding fields to fade in and out,
            //creating a pulse wave
            //Only for buttons whose scan numbers are revealed(bomb or no bomb)
            if (mainfield.getval(row, fincol) == 3 || mainfield.getval(row, fincol) == 4)
            {
                bombbutton = testbutton[row][fincol];
                Animation fade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadinng);
                bombbutton.setAnimation(fade);
                bombbutton.setText(getString(R.string.scantimeres, mainfield.scanbombs(row, fincol)));
                //System.out.println("bombing"+getString(R.string.scantimeres, mainfield.scanbombs(row, fincol)));
                testbutton[row][fincol] = bombbutton;

            }
        }
    }

    //Checks if game is won. If game is won, reverts the scan number and number of bombs defused
    //to zero. Displays the alert dialogue as well. It also calculates a top score.
    private void wincheck() {
        if(mainfield.getDefuseno()==mainfield.getBombno())
        {
            settimesplayed();
            if(mainfield.getRowno()==4)
            { setscore1(mainfield.getScannum()); }
            if(mainfield.getRowno()==5)
            { setscore2(mainfield.getScannum()); }
            if(mainfield.getRowno()==6)
            { setscore3(mainfield.getScannum()); }
            mainfield.setScannum(0);
            mainfield.setDefuseno(0);
            FragmentManager winmsgfrag=getSupportFragmentManager();
            WinMessageFragment winmsg=new WinMessageFragment();
            winmsg.show(winmsgfrag,"Win Message");
            recreate();
        }
    }


        //following preferences for editing the times played
    public static int gettimesplayedpref(Context context){
        int deftimesplayed = context.getResources().getInteger(R.integer.defaultplaytime);
        SharedPreferences timesplayedpref = context.getSharedPreferences("playtime", MODE_PRIVATE);
        return timesplayedpref.getInt("playtimeint",deftimesplayed);
    }

    private void settimesplayed() {
        mainfield.increasetimesplayed();
        SharedPreferences timesplayedpref = this.getSharedPreferences("playtime", MODE_PRIVATE);
        SharedPreferences.Editor editor = timesplayedpref.edit();
        editor.putInt("playtimeint", mainfield.getTimesplayed());
        editor.apply();
    }


    //Following preferences are for editing top scores
    //For 4x6 storing scores
    public static int getscoremode1(Context context){
        int score1 = context.getResources().getInteger(R.integer.defaultscore1);
        SharedPreferences scoremode1 = context.getSharedPreferences("scoremode1", MODE_PRIVATE);
        return scoremode1.getInt("scoremodeone",score1);
    }
    private void setscore1(int score) {
        if(score<getscoremode1(this))
        {
            mainfield.setTopscoremode1((score));
        }
        if(mainfield.getTopscoremode1()==0){
            mainfield.setTopscoremode1(score);
        }

        SharedPreferences scoremode1 = this.getSharedPreferences("scoremode1", MODE_PRIVATE);
        SharedPreferences.Editor editor = scoremode1.edit();
        editor.putInt("scoremodeone", mainfield.getTopscoremode1());
        editor.apply();
    }
    //For 5x10, storing scores

    public static int getscoremode2(Context context){
        int score2 = context.getResources().getInteger(R.integer.defaultscore1);
        SharedPreferences scoremode1 = context.getSharedPreferences("scoremode2", MODE_PRIVATE);
        return scoremode1.getInt("scoremodetwo",score2);
    }
    private void setscore2(int score) {
        if(score<getscoremode2(this))
        {
            mainfield.setTopscoremode2((score));
        }
        if(mainfield.getTopscoremode2()==0){
            mainfield.setTopscoremode2(score);
        }

        SharedPreferences scoremode2 = this.getSharedPreferences("scoremode2", MODE_PRIVATE);
        SharedPreferences.Editor editor = scoremode2.edit();
        editor.putInt("scoremodetwo", mainfield.getTopscoremode2());
        editor.apply();
    }

//For 6x15, storing scores

public static int getscoremode3(Context context){
    int score3 = context.getResources().getInteger(R.integer.defaultscore1);
    SharedPreferences scoremode3 = context.getSharedPreferences("scoremode3", MODE_PRIVATE);
    return scoremode3.getInt("scoremodethree",score3);
}
    private void setscore3(int score) {
        if(score<getscoremode3(this))
        {
            mainfield.setTopscoremode3((score));
        }
        if(mainfield.getTopscoremode3()==0){
            mainfield.setTopscoremode3(score);
        }

        SharedPreferences scoremode3 = this.getSharedPreferences("scoremode3", MODE_PRIVATE);
        SharedPreferences.Editor editor = scoremode3.edit();
        editor.putInt("scoremodethree", mainfield.getTopscoremode3());
        editor.apply();
    }


//For locking button sizes, so that changing amount of image will not impact
    //viewing
    private void lockButtonSizes() {
        for(int row = 0; row < mainfield.getRowno(); row++){
            for(int col = 0; col < mainfield.getColno(); col++)
            {
                Button testbutton2 = testbutton[row][col];
                int width = testbutton2.getWidth();
                testbutton2.setMinWidth(width);
                testbutton2.setMaxWidth(width);
                int height = testbutton2.getHeight();
                testbutton2.setMinHeight(height);
                testbutton2.setMaxHeight(height);
            }
        }
    }

    //updating ui after run
    private void finalui() {
        TextView defuser=(TextView)(findViewById(R.id.bombsfound));
        TextView scanner=(TextView)(findViewById(R.id.Scansused));
        defuser.setText(getString((R.string.Defused),mainfield.getDefuseno(),mainfield.getBombno()));
        scanner.setText(getString((R.string.Scans),mainfield.getScannum()));
    }


    //Adding the bomb image after a bomb is found, as well as adding sound and vibrations
    //at finding a bomb
    private void bombfound(int final_row, int final_col) {
        final MediaPlayer bombfound=MediaPlayer.create(this,R.raw.explosion);
        Vibrator rightvibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        rightvibration.vibrate(100);
        bombfound.start();
        Button bombbutton = testbutton[final_row][final_col];
        int newWidth = bombbutton.getWidth();
        int newHeight = bombbutton.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.therealbomb);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        bombbutton.setBackground(new BitmapDrawable(resource, scaledBitmap));
        bombbutton.setText("");
    }

    //Adding sound and vibration at the wrong choice i.e. bomb not found
    private void wrong_buzzersound() {
        Vibrator wrongvibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        wrongvibration.vibrate(300);
        final MediaPlayer bombnotfound=MediaPlayer.create(this,R.raw.buzzer);
        bombnotfound.start();
    }

}