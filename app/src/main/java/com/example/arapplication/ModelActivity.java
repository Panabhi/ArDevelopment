package com.example.arapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ModelActivity extends AppCompatActivity {

    ArrayList<ModelData> modelArrayList;
    DataAdapter dataAdapter;
    FirebaseFirestore fb;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    Button launch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);



        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        launch = findViewById(R.id.launch);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fb = FirebaseFirestore.getInstance();
        modelArrayList = new ArrayList<ModelData>();
        dataAdapter = new DataAdapter(this,modelArrayList);


        Intent intent = getIntent();
        String modelName= intent.getStringExtra("model_name").toString();

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModelActivity.this,MainActivity.class);
                intent.putExtra("model_name",modelName);
                startActivity(intent);
            }
        });
        ModelEventListener();

        recyclerView.setAdapter(dataAdapter);
    }

    private void ModelEventListener() {
        fb.collection("model_history").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
           if(error!=null)
           {
               if (progressDialog.isShowing())
                   progressDialog.dismiss();
               Log.e("Firestore error",error.getMessage());
               return;
           }
           for(DocumentChange dc:value.getDocumentChanges())
           {
               if(dc.getType() == DocumentChange.Type.ADDED)
               {
                   modelArrayList.add(dc.getDocument().toObject(ModelData.class));
               }
               dataAdapter.notifyDataSetChanged();
               if (progressDialog.isShowing())
                   progressDialog.dismiss();
           }
            }
        });
    }
}