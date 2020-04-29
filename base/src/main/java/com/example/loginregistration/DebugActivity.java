package com.example.loginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration.businessObj.business;
import com.example.loginregistration.fbclass.Account;
import com.example.loginregistration.fbclass.Project;
import com.example.loginregistration.fbclass.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
    }


    public void Logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }

    public void Search(View view){
        startActivity(new Intent(getApplicationContext(),search.class));
    }

    public void Feed(View view){
        startActivity(new Intent(getApplicationContext(),Feed.class));
    }

    public void Profile(View view){
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }
    public void MoneyAccount(View view){
        startActivity(new Intent(getApplicationContext(),MoneyAccount.class));
    }
}
