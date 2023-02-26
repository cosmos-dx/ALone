package com.idontknow.alone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    MyDbCode db;
    EditText login_pass;
    TextView signin_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = new MyDbCode(this);
        login_pass = findViewById(R.id.login_pass);

        login_pass.setText("");

        signin_btn = findViewById(R.id.signin_btn);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = login_pass.getText().toString();
                if(db.Ismatched(pass)){
                    Intent i = new Intent(getApplicationContext(), FinalScreen.class);
                    startActivity(i);
                }
                else {
                    //login_pass.setError("Incorrect Password");
                    login_pass.setError("Incorrect Password");
                }
            }
        });

    }

}