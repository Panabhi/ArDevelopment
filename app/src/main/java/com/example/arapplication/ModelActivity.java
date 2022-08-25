package com.example.arapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arapplication.DataAdapter;
import com.example.arapplication.MainActivity;
import com.example.arapplication.ModelData;
import com.example.arapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModelActivity extends AppCompatActivity {

    ArrayList<ModelData> modelArrayList;
    DataAdapter dataAdapter;
    FirebaseFirestore fb;
    RecyclerView recyclerView;
    CircleImageView imageView;
    TextView textView;
    ProgressDialog progressDialog;
    static final String TAG="read data ";

    TextView txtdata;
    Button launch;
    Button fab;
    String modelname;

    public FloatingActionButton floatingActionButton,fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        imageView = findViewById(R.id.circularimage);
        txtdata = findViewById(R.id.txtsetdata);
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
        //recyclerView = findViewById(R.id.recyclerview);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fb = FirebaseFirestore.getInstance();
        //modelArrayList = new ArrayList<ModelData>();
        //dataAdapter = new DataAdapter(this, modelArrayList);
        fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelActivity.this,AssesmentActivity.class);
                startActivity(intent);
                Toast.makeText(ModelActivity.this,"Quiz Started",Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = getIntent();
        modelname = intent.getStringExtra("model_name").toString();
        ModelEventListener();

        //recyclerView.setAdapter(dataAdapter);
    }

    private void ModelEventListener() {
//        fb.collection("model_history").document(modelname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//             if(progressDialog.isShowing())
//                 progressDialog.dismiss();
////             if(documentSnapshot!=null){
////             String modelinfo = documentSnapshot.getString("data").toString();
////             txtdata.setText(modelinfo);
////            }
////             else {
////                 progressDialog.dismiss();
////                 Toast.makeText(ModelActivity.this,"Data Not found",Toast.LENGTH_SHORT).show();
////             }
//                String modelinfo =modelname;
//             txtdata.setText(modelinfo);
//
//             }
//
//        });
        fb.collection("model_history").whereEqualTo("model_name",modelname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                        if(task.isSuccessful()){
//                                    Toast.makeText(MainActivity.this," ",Toast.LENGTH_SHORT)
//                                            .show();
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.d(TAG,document.getId()+"=>" + document.getData());
                                String text=document.get("data").toString();
                                txtdata.setText(text);
                                Picasso.with(ModelActivity.this).load(document.get("url").toString()).into(imageView);

                            }

                        }
                        else{
                            Toast.makeText(ModelActivity.this,"Failed",Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
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
                    Toast.makeText(this,"Model built", Toast.LENGTH_SHORT ).show();
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