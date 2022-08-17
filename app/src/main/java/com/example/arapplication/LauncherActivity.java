package com.example.arapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends AppCompatActivity {

    ImageView img_open;
    TextView txt_bottom , txt_bo;
    Animation top_anim;
    Animation bottom_anim;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

       // img_open = findViewById(R.id.img_open);
        txt_bottom = findViewById(R.id.txt_bottom);
        txt_bo = findViewById(R.id.txt_bo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();
        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//        img_open.startAnimation(top_anim);
        timer = new Timer();
        txt_bottom.startAnimation(bottom_anim);
        txt_bo.startAnimation(bottom_anim);
        Intent intent = new Intent(LauncherActivity.this,AfterLauncherActivity.class);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },4000);


    }
}