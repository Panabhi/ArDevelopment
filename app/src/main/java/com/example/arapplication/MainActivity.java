package com.example.arapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    String getmodelname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        getmodelname = intent.getStringExtra("model_name").toString();
        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef=storage.getReference().child(getmodelname+".glb");


        ArFragment arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        findViewById(R.id.button).setOnClickListener(v->{

                    try {
                        File file= File.createTempFile(getmodelname,"glb");
                        modelRef.getFile(file).addOnSuccessListener(taskSnapshot -> buildModel(file));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

        if (arFragment != null) {
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
        }
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
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });


    }

}