package com.idontknow.alone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FinalScreen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton add_data;
    Animation roopen,roclose,frombott,tobott;
    Boolean isMyFloatingOpen = false;
    MyDbCode db;

    RecyclerView recyclerView;
    ArrayList<String> data_key, value_book;
    CustomAdapter customAdapter;
    BottomSheetBehavior bottomSheetBehavior;


    EditText seach_key;

    ImageView logout_img;

    //Forgestures




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //OnCreate

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        db = new MyDbCode(this);

        recyclerView = findViewById(R.id.recHome);

        seach_key = findViewById(R.id.seach_key);
        seach_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                data_key.clear();
                value_book.clear();
                storeDataInArraysSearch(s.toString());
                customAdapter = new CustomAdapter(FinalScreen.this,getApplicationContext(), data_key, value_book);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(FinalScreen.this));

            }
        });




        data_key = new ArrayList<>();
        value_book = new ArrayList<>();



        storeDataInArrays();

        customAdapter = new CustomAdapter(FinalScreen.this,this, data_key, value_book);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FinalScreen.this));


        View bottomsheet = findViewById(R.id.design_bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        bottomSheetBehavior.setPeekHeight(100);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setDraggable(true);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }


            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        //Logging Out

        Vibrator vibe = (Vibrator) this.getSystemService(getApplicationContext().VIBRATOR_SERVICE) ;
        logout_img = findViewById(R.id.logout_img);
        logout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibe.vibrate(70);
                FinalScreen.this.finish();

                System.exit(0);
            }
        });



        // Floating Buttons
        add_data = (FloatingActionButton) findViewById(R.id.add_data);

        //Animation on Floating Buttons

        roopen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        roclose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        frombott = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        tobott = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        // Drawer Layouts
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);



        // Navigation Bar ---->


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.changepass:
                        Intent i = new Intent(FinalScreen.this,RegistrationActivity.class);
                        startActivity(i);
                        return true;

                    case R.id.deleteall:
                        AlertDeleteAll();



                }
                return true;
            }
        });

        //floating button ----->



        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMyfloatingButtons();
                openDialogue();

            }
        });


        //gesture work----->





        //Oncreate End
    }


    //override on touch event



    private void animateMyfloatingButtons(){
        if(isMyFloatingOpen){
            add_data.startAnimation(frombott);
        }else {
            add_data.startAnimation(tobott);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllData();

        while (cursor.moveToNext()){
            data_key.add(cursor.getString(0));
            value_book.add(cursor.getString(1));

        }

    }

    void storeDataInArraysSearch(String s){
        Cursor cursor = db.readDatastr(s);

        while (cursor.moveToNext()){
            data_key.add(cursor.getString(0));
            value_book.add(cursor.getString(1));

        }

    }

    public void openDialogue(){
        MyDialogue myDialogue = new MyDialogue();
        myDialogue.show(getSupportFragmentManager(), "Add Key-Title");


    }

    public void AlertDeleteAll(){
        AlertDialog.Builder alert =  new AlertDialog.Builder(this);
        alert.setTitle("Delete All ?");
        alert.setMessage("Do you really want to delete all data ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteAllData();
                Intent i = new Intent(FinalScreen.this, FinalScreen.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
                Toast.makeText(FinalScreen.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create().show();
    }



}