package com.example.loginregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration.adapter.AdapterBusiness;
import com.example.loginregistration.adapter.AdapterChange;
import com.example.loginregistration.businessObj.business;
import com.example.loginregistration.fbclass.Change;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileSearch extends AppCompatActivity {
    String bid;
    DatabaseReference db;
    ArrayList<Change> list;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    AdapterChange adapter;
    business bus;
    final String TAG = "DEBUG_PSEARCH";
    TextView bName;
    TextView bDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search);

        Intent intent = getIntent();
        bid = intent.getStringExtra("bid");
        mRecyclerView = findViewById(R.id.BusinessHistoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        bName = findViewById(R.id.textName);
        bDescription = findViewById(R.id.textView2);

        db = FirebaseDatabase.getInstance().getReference("/Business/");
        db.child(bid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bus = dataSnapshot.getValue(business.class);
                    bName.setText(bus.getName());
                    String des = "Country: " + bus.getCountry() + "\nDescription: " + bus.getDescription();
                    bDescription.setText(des);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value");
            }
        });

        db = FirebaseDatabase.getInstance().getReference("/history/business/");
        list = new ArrayList<>();
        adapter = new AdapterChange(list);
        mRecyclerView.setAdapter(adapter);

        db.child(bid).addListenerForSingleValueEvent(new ValueEventListener() {
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
    }

    public void BtnInvesti(View view){
        Intent intent = new Intent(getApplicationContext(),InvestmentAction.class);
        intent.putExtra("bid",bid);
        startActivity(intent);
    }

}
