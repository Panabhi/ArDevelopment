package com.example.arapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AfterLauncherActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    ModelAdapter modelAdapter;
    ProgressDialog progressDialog;
    ArrayList<ModelClass> modelClassArrayList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    RelativeLayout relative1, relative2, relative3, relative4, relative5, relative6, relative7, relative8, relative9, relative10;
    TextView txtsuntemple;
    EditText search_action;
    Button search_voice_btn;


    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_launcher);


        relative1 = findViewById(R.id.relative1);
        relative2 = findViewById(R.id.relative2);
        relative3 = findViewById(R.id.relative3);
        relative4 = findViewById(R.id.relative4);
        relative5 = findViewById(R.id.relative5);
        relative6 = findViewById(R.id.relative6);
        relative7 = findViewById(R.id.relative7);
        relative8 = findViewById(R.id.relative8);
        relative9 = findViewById(R.id.relative9);
        relative10 = findViewById(R.id.relative10);


        initclicklistners();
        // getActionBar().hide();
        //progressDialog = new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        // progressDialog.setMessage("Loading List");
        //progressDialog.show();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar.setTitle("VIDURV");
        }*/
        //recyclerView= findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
        //db = FirebaseFirestore.getInstance();
        //frameLayout = findViewById(R.id.framelayout);
        //modelClassArrayList = new ArrayList<ModelClass>();
        /*(toolbar!=null)
        {
            setSupportActionBar(toolbar);
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        //  Toast.makeText(AfterLoginActivity.this,"Dashboard",Toast.LENGTH_SHORT).show();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });*/
        //EventChangeListener();
        // modelAdapter = new ModelAdapter(AfterLauncherActivity.this, modelClassArrayList);
        //search_action = findViewById(R.id.search);
       /* search_action.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //filter(editable.toString());
            }
        });*/
        //recyclerView.setAdapter(modelAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.About_Us) {
            Toast.makeText(this, "Opened About us", Toast.LENGTH_SHORT);
        } else if (itemId == R.id.search_voice_btn) {
            //Toast.makeText(this, "Voice Search", Toast.LENGTH_SHORT);
            //voice = findViewById(R.id.search_voice_btn);
            // voice.setOnClickListener(new View.OnClickListener() {
            // @Override
            // public void onClick(View v) {
            openvoice();



        } else if (itemId == R.id.search) {
            Toast.makeText(this, "text Search", Toast.LENGTH_SHORT);
        } else {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initclicklistners() {
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "sunTemple");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Sun Temple", Toast.LENGTH_SHORT).show();
            }
        });
        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "RedFort");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Red Fort", Toast.LENGTH_SHORT).show();
            }
        });
        relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "IndiaGate");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "India Gate", Toast.LENGTH_SHORT).show();
            }
        });
        relative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "Qutub Minar");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Qutub Minar", Toast.LENGTH_SHORT).show();
            }
        });
        relative5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "GatewayofIndia");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Gateway of India", Toast.LENGTH_SHORT).show();
            }
        });
        relative6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "mysore palace");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Mysore Palace", Toast.LENGTH_SHORT).show();
            }
        });
        relative7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "moti masjid");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Moti Masjid", Toast.LENGTH_SHORT).show();
            }
        });
        relative8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "Jama-Masjid");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Jama Masjid", Toast.LENGTH_SHORT).show();
            }
        });
        relative9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "jantar mantar");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Jantar Mantar", Toast.LENGTH_SHORT).show();
            }
        });
        relative10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLauncherActivity.this, VoicePresentActivity.class);
                intent.putExtra("model_name", "Victoria memorial");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Victoria Memorial", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void EventChangeListener() {
        db.collection("Model").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        modelClassArrayList.add(dc.getDocument().toObject(ModelClass.class));
                    }
                    modelAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }

        });
    }


    private void replaceFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(name);
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AfterLauncherActivity.this);
        builder.setMessage("Do you want to close the app");
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private static final int SPEECH_REQUEST_CODE=0;

    private void openvoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice = results.get(0);
           // Toast.makeText(this, voice, Toast.LENGTH_SHORT).show();
        } //else {
            //Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode,data);
        }

        /*public void filter(String text) {
        ArrayList<Country> filterList = new ArrayList<>();
        for(Country items: countryArrayList)
        {
            if(items.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(items);
            }
        }
        myAdapter.filterList(filterList);
        }*/
    }


