package com.example.arapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Activity2Activity extends AppCompatActivity {

    ImageView btnpuz,btnquiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        btnpuz = findViewById(R.id.imgpuzzel);
        btnquiz = findViewById(R.id.imgquiz);
        btnquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2Activity.this, Assesment2Activity.class);
                startActivity(intent);
            }
        });
    }
}