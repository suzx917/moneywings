package com.example.loginregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.loginregistration.fbclass.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.widget.*;
import android.view.*;
import android.text.*;
import android.content.*;

/***************************************************************************************
*    Title: How to Create a Simple Login and Registration App in Android Studio That Displays Username Using Firebase
*    Author: Samuel Ochuba
*    Date: Sep 25, 2019
*    Availability: https://medium.com/@esambooks/how-to-create-a-simple-login-and-registration-app-in-android-studio-that-displays-username-
*                  using-674f21a0c208
*
***************************************************************************************/


public class registration extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPasswordConfirm;
    TextView mAlready;
    Button mRegButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mFullName = findViewById(R.id.FullName);
        mEmail    = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPasswordConfirm = findViewById(R.id.PasswordConfirm);
        mRegButton = findViewById(R.id.RegButton);
        mAlready = findViewById(R.id.Already);
        fAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String Fname = mFullName.getText().toString().trim();
                String[] FLname = Fname.split(" ");
                final String first = FLname[0];
                final String last = FLname[1];
                String PasswordConfirm = mPasswordConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password was not entered");
                    return;
                }
                if(password.compareTo(PasswordConfirm) != 0){

                    mPasswordConfirm.setError("Password does not match");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                         Toast.makeText(registration.this, "User Created", Toast.LENGTH_SHORT).show();
                         FirebaseUser user = fAuth.getCurrentUser();
                         String userID = user.getUid();
                         DatabaseReference regData = FirebaseDatabase.getInstance().getReference();

                         // add reg info to /user/{uid}/
                         regData.child("user").push();
                         regData.child("user").child(userID).child("email").setValue(email);
                         regData.child("user").child(userID).child("first").setValue(first);
                         regData.child("user").child(userID).child("last").setValue(last);
                         regData.child("user").child(userID).child("password").setValue(password);

                         // TODO: initialize data
                         // user type (1=regular 2=business)

                         // Account
                         Account acc = new Account(userID,1, email,0f);
                         Toast.makeText(registration.this, "new acc", Toast.LENGTH_SHORT).show();
                         regData.child("account").push();
                         regData.child("account").child(userID).setValue(acc);
                         Toast.makeText(registration.this, "Account Created", Toast.LENGTH_SHORT).show();


                         // Jump to debug screen
                         startActivity(new Intent(getApplicationContext(), DebugActivity.class));

                        }else{
                         Toast.makeText(registration.this, "Error ! =" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
}}

