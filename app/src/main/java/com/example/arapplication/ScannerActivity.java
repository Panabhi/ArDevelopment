package com.example.arapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScannerActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;

    PreviewView previewView;
    TextView textView;

    private int REQUEST_CODE_PERMISSION = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[] {"android.permission.CAMERA"};
    List<String> imagenet_classes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        previewView = findViewById(R.id.cameraView);
        textView = findViewById(R.id.result_text);

        if(!checkPermissions()){
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,REQUEST_CODE_PERMISSION);
        };

        imagenet_classes = LoadClasses("imagenet-classes.txt");
        loadtorchModel("model.ptl");

        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(ScannerActivity.this);
        cameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                startCamera(cameraProvider);

            }catch (ExecutionException | InterruptedException e){
            //errors
            }
        },ContextCompat.getMainExecutor(ScannerActivity.this));
    }

    private boolean checkPermissions()
    {
        for(String permission: REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    Executor executor = Executors.newSingleThreadExecutor();
    void startCamera(@NonNull ProcessCameraProvider cameraProvider){
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(224,224))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                int rotation = image.getImageInfo().getRotationDegrees();
                analyzeImage(image,rotation);
                image.close();
            }
        });
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this,cameraSelector,preview);
    }


    void loadtorchModel(String fileName){
        File modelFile = new File(this.getFilesDir(),fileName);
        try{
            if(!modelFile.exists()){
                InputStream inputStream = getAssets().open(fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(modelFile);
                byte[] buffer = new byte[2048];
                int bytesRead = -1;
                while((bytesRead = inputStream.read(buffer)) !=-1){
                    fileOutputStream.write(buffer,0,bytesRead);
                }
                inputStream.close();
                fileOutputStream.close();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    Module module;
    void analyzeImage(ImageProxy image,int rotation){
        @SuppressLint("UnsafeOptInUsageError") Tensor inputTensor = TensorImageUtils.imageYUV420CenterCropToFloat32Tensor(Objects.requireNonNull(image.getImage()),rotation,224,224,
                TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,TensorImageUtils.TORCHVISION_NORM_STD_RGB);

        Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
        float[] scores = outputTensor.getDataAsFloatArray();
        float maxscores = -Float.MAX_VALUE;
        int maxscoreIdx = -1;
        for(int i=0;i<scores.length;i++)
        {
            if(scores[i]>maxscores){
                maxscores = scores[i];
                maxscoreIdx = i;
            }
        }
        String classResult = imagenet_classes.get(maxscoreIdx);
        Log.v("Torch","Detected:"+classResult);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(classResult);
                // Toast.makeText(ScannerActivity.this,classResult,Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<String> LoadClasses(String fileName){
        List<String> classes = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            String line;
            while((line = br.readLine())!=null)
            {
                classes.add(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return classes;
    }
}