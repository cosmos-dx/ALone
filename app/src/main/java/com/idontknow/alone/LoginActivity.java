package com.idontknow.alone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    MyDbCode db;
    EditText login_pass;
    TextView signin_btn, info_pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = new MyDbCode(this);
        login_pass = findViewById(R.id.login_pass);

        login_pass.setText("");
        info_pass = findViewById(R.id.infopass);

        login_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = login_pass.getText().toString();
                if(db.Ismatched(pass)){
                    info_pass.setText("Matched");
                    info_pass.setTextColor(Color.parseColor("#0000FF"));
                    Intent i = new Intent(getApplicationContext(), FinalScreen.class);
                    startActivity(i);

                }
                else{
                    info_pass.setText("Not Matching");
                   info_pass.setTextColor(Color.parseColor("#FF0000"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        signin_btn = findViewById(R.id.signin_btn);
//        signin_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String pass = login_pass.getText().toString();
//                if(db.Ismatched(pass)){
//                    Intent i = new Intent(getApplicationContext(), FinalScreen.class);
//                    startActivity(i);
//                }
//                else {
//                    //login_pass.setError("Incorrect Password");
//                    login_pass.setError("Incorrect Password");
//                }
//            }
//        });

    }

}