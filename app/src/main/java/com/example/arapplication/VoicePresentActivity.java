package com.example.arapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VoicePresentActivity extends AppCompatActivity {

    Button loadmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_present);
        Intent intent = getIntent();
        String model_name = intent.getStringExtra("model_name").toString();
        loadmodel = findViewById(R.id.btnmodel);
        loadmodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoicePresentActivity.this,MainActivity.class);
                intent.putExtra("model_name",model_name);
                startActivity(intent);
            }
        });
    }
}