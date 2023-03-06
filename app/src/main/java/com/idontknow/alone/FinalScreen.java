package com.idontknow.alone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FinalScreen extends AppCompatActivity implements DialogInterface.OnDismissListener,
        MyDialogue.MydiaInterface, CustomAdapter.MyAdapterInterface{
    //------------------------------------------------------------------------------- Initialization

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
        EncryptDecrypt encdcp;
    //------------------------------------------------------------------------------- Initialization


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //------------------------------------------------------------------------------------------------------------------------------------------------ OnCreate



        super.onCreate(savedInstanceState);
        db = new MyDbCode(this);


        setContentView(R.layout.activity_final_screen);

        recyclerView = findViewById(R.id.recHome);
        seach_key = findViewById(R.id.seach_key);

        //--------------------------------------------------------------------------------- OnSearch



        seach_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbRefresh(s.toString());
                switch (s.toString()){
                    case "#close":
                        closeapp();
                    case "#smile":
                        Intent Intent3=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                        startActivity(Intent3);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                dbRefresh(s.toString());

                new CustomAdapter(FinalScreen.this,getApplicationContext(), data_key, value_book);
            }
        });

        //--------------------------------------------------------------------------------- OnSearch

        //------------------------------------------------------------------ Filling in RecyclerView

        data_key = new ArrayList<>();
        value_book = new ArrayList<>();
        storeDataInArrays();
        customAdapter = new CustomAdapter(FinalScreen.this,this, data_key, value_book);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FinalScreen.this));

        //------------------------------------------------------------------ Filling in RecyclerView

        //------------------------------------------------------------ BottomSheet(neeche wala Menu)
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

        //------------------------------------------------------------ BottomSheet(neeche wala Menu)




        //-------------------------------------------------------------------------------Logging Out

        Vibrator vibe = (Vibrator)
                this.getSystemService(getApplicationContext().VIBRATOR_SERVICE) ;
        logout_img = findViewById(R.id.logout_img);
        logout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(70);
                finishAffinity();
                finish();
                System.exit(0);
            }
        });

        //-------------------------------------------------------------------------------Logging Out




//        KeyguardManager myKM = (KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
//        if( myKM.inKeyguardRestrictedInputMode()) {
//            //it is locked
//            vibe.vibrate(70);
//            finishAffinity();
//            finish();
//            System.exit(0);
//        } else {
//            //it is not locked
//        }


        //------------------------------------------------------------------------- Floating Buttons

        add_data = (FloatingActionButton) findViewById(R.id.add_data);
        //Animation on Floating Buttons
        roopen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        roclose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        frombott = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        tobott = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMyfloatingButtons();
                openDialogue();
//                Intent intent = getIntent();
//                String key_title = intent.getStringExtra("key");
//                data_key.add(key_title);

            }
        });

        //------------------------------------------------------------------------- Floating Buttons


        //--------------------------------------------------------------------------- Drawer Layouts
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);
        // Navigation Bar ---->
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             switch(item.getItemId()){
                 case R.id.changepass:
                     Intent i = new
                             Intent(FinalScreen.this,RegistrationActivity.class);
                     startActivity(i);
                     return true;
                 case R.id.deleteall:
                     AlertDeleteAll();
                     drawerLayout.closeDrawer(Gravity.LEFT);

                 case R.id.aboutapp:
                     dbExport expdb = new dbExport();

                 case R.id.shareapp:
                     try {
                         Intent shareIntent = new Intent(Intent.ACTION_SEND);
                         shareIntent.setType("text/plain");
                         shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ALone (All password one Place)");
                         String shareMessage= "\nOffline app to secure your password at one place\n\n";
                         shareMessage = shareMessage + "https://github.com/cosmos-dx/ALone" + "\n\n"; //BuildConfig.APPLICATION_ID +"\n\n";
                         shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                         startActivity(Intent.createChooser(shareIntent, "choose one"));
                     } catch(Exception e) {
                         //e.toString();
                     }

                 case R.id.aboutcheat:
                     ArrayList<String> cheat = new ArrayList<>();
                     cheat.add("#close");
                     cheat.add("#smile");
                     AlertCheat(cheat);




             }
             return true;
         }
     });

        //--------------------------------------------------------------------------- Drawer Layouts


        //------------------------------------------------------------------------------------------------------------------------------------------------ OnCreate
    }


    //------------------------------------------------------------------------------ Class Functions
    private void animateMyfloatingButtons(){
        if(isMyFloatingOpen){
            add_data.startAnimation(frombott);
        }else {
            add_data.startAnimation(tobott);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent
            data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    public void storeDataInArrays(){
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
        Bundle args = new Bundle();
        args.putString("title", "Add Sample Title");
        args.putString("DisplayText", "Once Upon a time There was a king.. ");
        args.putString("KeyId", "AnyThing Required... ");
        args.putString("Key1", "Values222");
        args.putString("Key2", "Values 3333");
        args.putBoolean("SHOW_VIEW_INPUT_DIALOG", true); // will show on onCreateView
        myDialogue.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        myDialogue.show(ft, "dialog");
    }

    public void openUpdateDialogue(String key_titleUpdateDia, String key_valueUpdateDia){
        Update_frag update_frag = new Update_frag();
        Bundle args = new Bundle();
        args.putString("title", "Add Sample Title");
        args.putString("DisplayText", "Once Upon a time There was a king.. ");
        args.putString("KeyId", "Key-Id");
        args.putString("Key", key_titleUpdateDia);
        args.putString("Value", key_valueUpdateDia);
        args.putBoolean("SHOW_VIEW_INPUT_DIALOG", true); // will show on onCreateView
        update_frag.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        update_frag.show(ft, "dialog");
    }

    public void closeapp(){
        Vibrator vibe = (Vibrator)
                this.getSystemService(getApplicationContext().VIBRATOR_SERVICE) ;
        vibe.vibrate(50);
        finishAffinity();
        finish();
        System.exit(0);
    }

    public void AlertDeleteOne(String key_titleUpdateDia, String key_valueUpdateDia){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete "+ key_titleUpdateDia + " ?");
        alert.setMessage("Do you really want to delete " + key_titleUpdateDia + " data ?");

        Vibrator vibe = (Vibrator)
                this.getSystemService(getApplicationContext().VIBRATOR_SERVICE) ;
        vibe.vibrate(50);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteOneData(key_titleUpdateDia);
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

    public void AlertCheat(ArrayList<String > cheat){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Codes ?");
        StringBuffer sb = new StringBuffer();

        for (String s : cheat) {
            sb.append(s);
            sb.append(" ");
        }
        String str = sb.toString();
        alert.setMessage(str);
        alert.create().show();
    }

    public void AlertDeleteAll(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
    @Override

    public void onDismiss(DialogInterface dialogInterface) {
        Toast.makeText(getApplicationContext(), "messageDisplay OnDismiss Dialog....",
                Toast.LENGTH_LONG);
    }

    public void dbRefresh(String text){
        data_key.clear();
        value_book.clear();
        storeDataInArraysSearch(text);
        customAdapter = new CustomAdapter(FinalScreen.this,getApplicationContext(),
                data_key, value_book);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FinalScreen.this));
    }
    public void messageFromAloneFragment(String getMessage){
        seach_key.setText(getMessage);
        dbRefresh(getMessage);

    }

    @Override
    public void text(String key_title) {

        data_key.clear();
        storeDataInArrays();
        //data_key.add(key_title);
        customAdapter = new CustomAdapter(FinalScreen.this,this, data_key, value_book);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FinalScreen.this));
        recyclerView.scrollToPosition(data_key.size() - 4);
        //recyclerView.getItemDecorationAt(data_key.size()-1);

    }

    public void getFinalMsg(){
        Toast.makeText(this, "this is long", Toast.LENGTH_SHORT).show();
    }


    //------------------------------------------------------------------------------ Class Functions
}