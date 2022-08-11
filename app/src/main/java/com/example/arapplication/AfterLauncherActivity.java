package com.example.arapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    ModelAdapter modelAdapter;
    ProgressDialog progressDialog;
    ArrayList<ModelClass> modelClassArrayList;
    RecyclerView recyclerView;
    FirebaseFirestore db;

    private EditText search_action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_launcher);
        //getActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading List");
        progressDialog.show();
        toolbar = findViewById(R.id.toolbar);
        recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
        frameLayout = findViewById(R.id.framelayout);
        modelClassArrayList = new ArrayList<ModelClass>();
        if(toolbar!=null)
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
        });
        EventChangeListener();
        modelAdapter = new ModelAdapter(AfterLauncherActivity.this, modelClassArrayList);
        //search_action = findViewById(R.id.search);
        /*search_action.addTextChangedListener(new TextWatcher() {
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
        recyclerView.setAdapter(modelAdapter);
    }

    private void EventChangeListener() {
        db.collection("model").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    private void replaceFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
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


