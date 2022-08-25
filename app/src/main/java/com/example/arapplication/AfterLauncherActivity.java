package com.example.arapplication;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AfterLauncherActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<String> mnames;
    FrameLayout frameLayout;
    ArrayList<String> filterList;

    ArrayAdapter<String> adapter;
    ModelAdapter modelAdapter;
    MenuItem scanimage;
    ProgressDialog progressDialog;
    ArrayList<Model> modelClassArrayList;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    RelativeLayout relative1, relative2, relative3, relative4, relative5, relative6, relative7, relative8, relative9, relative10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_launcher);

        listView= findViewById(R.id.listView);

    //    progressDialog = new ProgressDialog(this);
      //  progressDialog.setCancelable(false);
        // progressDialog.setMessage("Loading List");
        //progressDialog.show();
        toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

       // recyclerView= findViewById(R.id.recyclerview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
       // db = FirebaseFirestore.getInstance();
        //frameLayout = findViewById(R.id.framelayout);
        //modelClassArrayList = new ArrayList<Model>();
        if(toolbar!=null)
        {
            setSupportActionBar(toolbar);
        }

        /*EventChangeListener();
        modelAdapter = new ModelAdapter(AfterLauncherActivity.this, modelClassArrayList, new ModelAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(Model model) {
                Intent intent = new Intent(AfterLauncherActivity.this, ModelActivity.class);
                String name = model.getModel_name().toString();
                intent.putExtra("model_name",name);
                startActivity(intent);
                //Toast.makeText(AfterLauncherActivity.this,name,Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(modelAdapter);
        Filter();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,filterList);
        listView.setAdapter(adapter);*/
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
                intent.putExtra("model_name", "Sanchi_Stupa");
                startActivity(intent);
                Toast.makeText(AfterLauncherActivity.this, "Sanchi Stupa", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.About_Us) {
            Toast.makeText(this, "Opened About us", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.search_voice_btn) {
            //Toast.makeText(this, "Voice Search", Toast.LENGTH_SHORT);
            //voice = findViewById(R.id.search_voice_btn);
            // voice.setOnClickListener(new View.OnClickListener() {
            // @Override
            // public void onClick(View v) {
            openvoice();



        } else if (itemId == R.id.search) {

        } else {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    private void EventChangeListener() {
        db.collection("model_history").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        modelClassArrayList.add(dc.getDocument().toObject(Model.class));
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

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();

        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       // SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //adapter.getFilter().filter(newText);
                processsearch(s);
                return false;
            }
            private void processsearch(String s) {
                //FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                       // .setQuery(FirebaseDatabase.getInstance().getReference().child("data"),model.class)
                       // .build();

            }
        });

        scanimage = menu.findItem(R.id.scanimage);
        scanimage.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(AfterLauncherActivity.this,ScannerActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
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

        public void Filter() {
        filterList = new ArrayList<>();
        for(Model items: modelClassArrayList)
        {
            if(items.getModel_name()!=null)
            {
                filterList.add(items.model_name);
            }
        }
    }


}


