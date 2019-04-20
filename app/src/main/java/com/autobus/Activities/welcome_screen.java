package com.autobus.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.autobus.Activities.landing_screen;
import com.autobus.R;

public class welcome_screen extends AppCompatActivity {
    private TextView textwelcome;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textwelcome = (TextView)findViewById(R.id.textwelcome);
        img = (ImageView)findViewById(R.id.img);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Raleway-Regular.ttf");
        textwelcome.setTypeface(typeface);

        Animation welcomeanim = AnimationUtils.loadAnimation(this, R.anim.welcome_animation);
        textwelcome.startAnimation(welcomeanim);
        img.startAnimation(welcomeanim);


        final Intent i = new Intent(this, landing_screen.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
    }