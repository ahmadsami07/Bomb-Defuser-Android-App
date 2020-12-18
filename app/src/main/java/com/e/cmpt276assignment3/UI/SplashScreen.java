package com.e.cmpt276assignment3.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.e.cmpt276assignment3.R;

/*
Following class creates the animation i.e. splashscreen before moving on to the main activity of the application.
 */
public class SplashScreen extends AppCompatActivity {
        ImageView blast;
        ImageView blast2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        blast=(ImageView)findViewById(R.id.bomb);
        blast2=(ImageView)findViewById(R.id.target);
        final Animation rotate=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotating);
        final Animation fadeout=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fading2);
        Animation slide=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sliding);

        blast2.setAnimation(slide);
        Button skip=(Button)findViewById(R.id.skipbutton);

        skip.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                        Intent mainact=MainActivity.makeIntent(SplashScreen.this);
                                        startActivity(mainact);
                                    }
                                });

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent mainact=MainActivity.makeIntent(SplashScreen.this);
                startActivity(mainact);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                blast2.setAnimation(fadeout);
                blast.setAnimation(rotate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}