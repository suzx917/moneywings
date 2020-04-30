package com.example.loginregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration.adapter.AdapterChange;
import com.example.loginregistration.fbclass.Account;
import com.example.loginregistration.fbclass.Change;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MoneyAccount extends AppCompatActivity {

    private TextView uName;
    private TextView uBalance;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DatabaseReference db;
    private ArrayList<Change> list;
    private AdapterChange adapter;
    private FbUtil fbu;
    private Account acc;
    private final String TAG = "DEBUG_MONEYACC";

    private EditText textAmount;
    private Button buttonDeposit;
    private Button buttonWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_account);

        uName = findViewById(R.id.textView8);
        uBalance = findViewById(R.id.textView10);
        mRecyclerView = findViewById(R.id.acc_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        textAmount = findViewById(R.id.editText4);
        textAmount.setText("");
        buttonDeposit = findViewById(R.id.button2);
        buttonWithdraw = findViewById(R.id.button3);

        // load account;
        fbu = new FbUtil();
        acc = new Account();
        db = FirebaseDatabase.getInstance().getReference().child("account").child(fbu.getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acc = dataSnapshot.getValue(Account.class);
                uName.setText(acc.getId());
                uBalance.setText(Double.toString(acc.getBalance()));
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
        db = FirebaseDatabase.getInstance().getReference("/history/user/");
        list = new ArrayList<>();
        adapter = new AdapterChange(list);
        mRecyclerView.setAdapter(adapter);


        db.child(fbu.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Change c = snapshot.getValue(Change.class);
                        list.add(c);
                        Log.e(TAG,"Added change");
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value");
            }
        });



        buttonDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_entry = textAmount.getText().toString();

                try {
                    final int amt = Integer.parseInt(user_entry.trim());
                    textAmount.setText("");
                    if ( amt > 0 ) {
                        fbu.deposit(amt, "Test Bank");
                    }
                }
                catch (NumberFormatException e) // just in case something weird happens
                {
                    Log.e(TAG, "Failed to deposit - " + e.toString());
                }
                refresh();
            }
        });

        buttonWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_entry = textAmount.getText().toString();

                try {
                    final int amt = Integer.parseInt(user_entry.trim());
                    textAmount.setText("");
                    if ( amt > 0 ) {
                        fbu.withdraw(amt, "Test Bank");
                    }
                }
                catch (NumberFormatException e) // just in case something weird happens
                {
                    Log.e(TAG, "Failed to withdraw - " + e.toString());
                }
                refresh();
            }
        });
    }

    public void refresh() {
        db.child(fbu.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Change c = snapshot.getValue(Change.class);
                        list.add(c);
                        Log.e(TAG,"Added change");
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value");
            }
        });
    }
}
