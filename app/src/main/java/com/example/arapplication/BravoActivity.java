package com.example.arapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class BravoActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    //After completion of 2000 ms, the next activity will get started.
    ImageView imageView;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bravo);
        imageView = findViewById(R.id.imageview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        timer = new Timer();
        loadanimation();
    }

    private void loadanimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        imageView.startAnimation(animation);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }
}