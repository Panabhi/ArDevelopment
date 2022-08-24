package com.example.arapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ModelActivity extends AppCompatActivity {

    ArrayList<ModelData> modelArrayList;
    DataAdapter dataAdapter;
    FirebaseFirestore fb;
    RecyclerView recyclerView;
    TextView textView;
    ProgressDialog progressDialog;

    Button launch;
    Button fab;
    String modelname;

    public FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s=(String) dataSnapshot.getValue();
                textView.setText(s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();
       // launch = findViewById(R.id.launch);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fb = FirebaseFirestore.getInstance();
        modelArrayList = new ArrayList<ModelData>();
        dataAdapter = new DataAdapter(this, modelArrayList);


        ModelEventListener();

        recyclerView.setAdapter(dataAdapter);
    }

    private void ModelEventListener() {
        fb.collection("model_history").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        modelArrayList.add(dc.getDocument().toObject(ModelData.class));
                    }
                    dataAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
        Intent intent = getIntent();
        modelname = intent.getStringExtra("model_name").toString();
        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef=storage.getReference().child(modelname+".glb");


        ArFragment arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v->{

            try {
                File file= File.createTempFile(modelname,"glb");
                modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        /*if (arFragment != null) {
            arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
//                anchorNode.setRenderable(renderable);
//
//                arFragment.getArSceneView().getScene().addChild(anchorNode);

                anchorNode.setParent(arFragment.getArSceneView().getScene());

                // Create the transformable andy and add it to the anchor.
                TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
// Maxscale must be greater than minscale
                node.getScaleController().setMaxScale(2f);
                node.getScaleController().setMinScale(0.9f);

                node.setParent(anchorNode);
                node.setRenderable(renderable);
                node.select();

            });
        }*/
    }

    private ModelRenderable renderable ;
    private void buildModel(File file) {

        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()),RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        ModelRenderable
                .builder()
                .setSource(this, renderableSource )
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this,"Model built",Toast.LENGTH_SHORT ).show();
                    renderable = modelRenderable;
                    Intent intent = getIntent();
                    String modelName = intent.getStringExtra("model_name").toString();

                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ModelActivity.this, MainActivity.class);
                            intent.putExtra("model_name", modelName);
                            startActivity(intent);
                        }
                    });
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });


    }

    }