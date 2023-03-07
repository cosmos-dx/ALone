package com.idontknow.alone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    MyDbCode db;
    EditText old_login_pass, new_login_pass,con_login_pass;
    TextView signin_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        db = new MyDbCode(this);

        old_login_pass = findViewById(R.id.old_login_pass);
        new_login_pass = findViewById(R.id.new_login_pass);
        con_login_pass = findViewById(R.id.con_login_pass);
        signin_btn = findViewById(R.id.signin_btn);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = old_login_pass.getText().toString();
                String newpass = new_login_pass.getText().toString();
                String conpass = con_login_pass.getText().toString();
                if(!db.Ismatched(old)){
                    old_login_pass.setError("Not Matched");
                }
                else {
                    if (newpass.equals(conpass)) {
                        db.update(old, newpass);
                        Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else {
                        con_login_pass.setError("Not Matched");
                    }
                }
            }
        });




    }
}