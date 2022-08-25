package com.example.arapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FolderActivity extends AppCompatActivity {


    ImageView imgmonuments,imgactivity,imghistory,imgpdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        imgmonuments = findViewById(R.id.imgmonuments);
        imghistory = findViewById(R.id.imghistory);
        imgactivity = findViewById(R.id.imgactivity);
        imgpdf = findViewById(R.id.imgpdf);
        imgmonuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FolderActivity.this,AfterLauncherActivity.class);
                startActivity(intent);
            }
        });
        imghistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FolderActivity.this,AfterLauncherActivity.class);
                startActivity(intent);
            }
        });
        imgactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FolderActivity.this,AfterLauncherActivity.class);
                startActivity(intent);
            }
        });
        imgpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FolderActivity.this,AfterLauncherActivity.class);
                startActivity(intent);
            }
        });
    }
}