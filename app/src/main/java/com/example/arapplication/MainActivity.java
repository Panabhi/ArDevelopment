package com.example.arapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private Button buttonSpeak;
    private EditText editText;
    FirebaseFirestore db;
    static final String TAG="read data ";


    String modelname;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= FirebaseFirestore.getInstance();

        tts = new TextToSpeech(this, this);
        buttonSpeak = (Button) findViewById(R.id.button1);

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                db.collection("model_history").whereEqualTo("model_name",modelname)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT)
                                            .show();
                                    for(QueryDocumentSnapshot document: task.getResult()){
                                        Log.d(TAG,document.getId()+"=>" + document.getData());
                                        text=document.get("data").toString();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
                speakOut();
            }


        });



        Intent intent = getIntent();
        modelname = intent.getStringExtra("model_name").toString();
        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef=storage.getReference().child(modelname+".glb");


        ArFragment arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        findViewById(R.id.button).setOnClickListener(v->{

                    try {
                        File file= File.createTempFile(modelname,"glb");
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
    @Override
    public void onDestroy() {
// Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                buttonSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}