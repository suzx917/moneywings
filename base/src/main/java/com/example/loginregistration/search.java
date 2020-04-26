package com.example.loginregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import com.example.loginregistration.adapter.AdapterBusiness;
import com.example.loginregistration.businessObj.business;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class search extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<business> list;
    RecyclerView mRecyclerView;
    SearchView searchView;
    AdapterBusiness adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);

        ref = FirebaseDatabase.getInstance().getReference().child("Business");

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        searchView = findViewById(R.id.search_bar);

        list = new ArrayList<>();
        adapter = new AdapterBusiness(list);
        mRecyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        business bs = snapshot.getValue(business.class);
                        list.add(bs);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value");
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s ) {
                lookup(s);
                return true;
            }
        });

    }

    private void lookup(String s) {
        ArrayList<business> myList = new ArrayList<>();
        for (business obj : list){
            if(obj.getName().toLowerCase().contains(s.toLowerCase()) || obj.getCountry().toLowerCase().contains(s.toLowerCase())){
                myList.add(obj);
            }
        }
        AdapterBusiness adapter = new AdapterBusiness(myList);
        mRecyclerView.setAdapter(adapter);
    }
}
