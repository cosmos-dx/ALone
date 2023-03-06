package com.idontknow.alone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

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

        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:

                //msgtex.setTextColor(Color.parseColor("#fafafa"));
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                //msgtex.setText("This device doesnot have a fingerprint sensor");

               // loginbutton.setVisibility(View.GONE);
                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                //msgtex.setText("The biometric sensor is currently unavailable");
               // loginbutton.setVisibility(View.GONE);
                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                //msgtex.setText("Your device doesn't have fingerprint saved,please check your security settings");
               // loginbutton.setVisibility(View.GONE);
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent i = new Intent(getApplicationContext(), FinalScreen.class);
                startActivity(i);

            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("You can Try Login")
                .setDescription("ALone Login ").setNegativeButtonText("Cancel").build();
        biometricPrompt.authenticate(promptInfo);

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