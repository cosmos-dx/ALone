package com.idontknow.alone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateData extends AppCompatActivity {

    EditText key_title, value;
    Button update_btn;

    String title ,val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        key_title = findViewById(R.id.edit_text_key);
        value = findViewById(R.id.edit_text_value);
        update_btn = findViewById(R.id.update_btn);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });





    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("title") && getIntent().hasExtra("value") ){

            title = getIntent().getStringExtra("title");
            val = getIntent().getStringExtra("value");

            key_title.setText(title);
            value.setText(val);

            Log.d("stev", title+" "+val);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + title + " ?");
        builder.setMessage("Are you sure you want to Update " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDbCode myDB = new MyDbCode(UpdateData.this);
                title = key_title.getText().toString().trim();
                val = value.getText().toString().trim();
                myDB.updateData(title, val);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.create().show();
    }

}