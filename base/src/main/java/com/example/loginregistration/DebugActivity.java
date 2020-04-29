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

    private Button button;
    private Button button2;
    private Button button3;
    private TextView text;
    private EditText et;
    private AlertDialog.Builder builder;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private FbUtil fbu;
    private Account acc;
    private business bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        text = findViewById(R.id.debugText);
        button = findViewById(R.id.debugButton);
        button2 = findViewById(R.id.debugButton2);
        button3 = findViewById(R.id.debugButton3);
        et = findViewById(R.id.editText);
        builder = new AlertDialog.Builder(DebugActivity.this);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        fbu = new FbUtil();

        // load account;
        acc = new Account();

        DatabaseReference dbRef =  db.getReference().child("account").child(user.getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acc = dataSnapshot.getValue(Account.class);
                text.setText("balance:\n"+acc.getBalance());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting data failed
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Database Error!", duration);
                toast.show();
            }
        });

        // Button Events
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printBalance();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    double amount = Double.parseDouble(et.getText().toString());
                    text.setText("input amount = " + amount);

                    if (amount <= 0) {
                        builder.setMessage("Invalid amount");
                    }
                    else {
                        fbu.deposit(amount, "Test Bank");

                    }
                }
                catch (Exception e) {
                    builder.setMessage("Invalid input");
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbu.invest(500,"01");
            }
        });

        text.setText("balance:\n"+acc.getBalance());
    }


    // call uid
    public void printUid() {

        if (user != null) {
            text.setText("User ID:\n" + user.getUid());
        }
    }


    // push a test project object thru util
    public void addP0() {
        Project p0 = new Project(fbu.getUid(), "A test project");
        String key = fbu.pushObject(p0,"project");
        text.setText("Project ID:\n" + key);
    }

    public void printBalance() {

            String str = "" + acc.getBalance();
            text.setText(str);
    }


    // ++ money
    public void deposit(double money) {

        // verification placeholder
        boolean ok = true;

        if (ok) {
            String uid = fbu.getUid();
            String path = "/account/" + uid;
            DatabaseReference dbRef = db.getReference(path);
            dbRef.child("balance").setValue(acc.getBalance() + money);
        }

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
