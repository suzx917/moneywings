package com.example.loginregistration;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.loginregistration.businessObj.business;
import com.example.loginregistration.fbclass.Account;
import com.example.loginregistration.fbclass.Change;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class FbUtil {

    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Account acc;
    private business bus;
    private static final String TAG = "DEBUG_FBU";

    public FbUtil() {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        acc = new Account();

        db.getReference().child("account").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acc = dataSnapshot.getValue(Account.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Database cancelled.");
            }
        });
    }

    // This will write 'obj' as json under /root/{path}/{key}
    // the key generated by firebase will be returned by this function
    public String pushObject(Object obj, String path) {
        DatabaseReference dbRef = db.getReference(path);
        String key = dbRef.push().getKey();
        dbRef.child(key).setValue(obj);
        return key;
    }

    public void testMsg() {
        DatabaseReference dbRef = db.getReference("/userdata/" + getUid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
        String str = "Test from -- " + sdf.format(new Date());
        dbRef.setValue(str);
    }

    public String getUid() {
        return user.getUid();
    }
    public String getUName() {
        return acc.getName();
    }
    public String getUBalance() {
        return Double.toString(acc.getBalance());
    }


    // Return 0 if succeed
    public int invest(final double amount, final String businessId) {
        Log.d(TAG, "Investing " + amount + " from user: " + user.getUid() + " to bid: " + businessId);

        double balance = acc.getBalance();
        if (amount > balance) {
            return 1;
        }

        db.getReference("/Business/01").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bus = dataSnapshot.getValue(business.class);
                Log.e("DEBUG_FBU", businessId + " value = " + bus.getValue());
                try {
                    // write record to user account
                    Change c1 = new Change("INVEST_TO", -amount, bus.getId(), "Business", bus.getName());
                    String path1 = "/history/user/" + user.getUid();
                    DatabaseReference dbRef1 = db.getReference(path1);
                    String key1 = dbRef1.push().getKey();
                    c1.setId(key1);
                    dbRef1.child(key1).setValue(c1);
                    // change user balance
                    db.getReference("/account/" + user.getUid()).child("balance").setValue(acc.getBalance() - amount);

                    // write record to business
                    Change c2 = new Change("INVEST_FROM", amount, acc.getId(), "User", acc.getName());
                    String path2 = "/history/business/" + bus.getId();
                    DatabaseReference dbRef2 = db.getReference(path2);
                    String key2 = dbRef2.push().getKey();
                    c2.setId(key2);
                    dbRef2.child(key2).setValue(c2);

                    // change business value
                    db.getReference("/Business/" + bus.getId()).child("value").setValue(bus.getValue() + amount);
                }
                catch (Exception e) {
                    Log.e(TAG, "Failed Investing - " + e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DEBUG_FBU", "Database cancelled.");
            }
        });

        return 0;
    }

    // Simulate the bank verification process
    public boolean bankVerification(double amount) {
        Random rnd = new Random();
        rnd.setSeed(0);
        return rnd.nextInt(7) > 0;
    }

    // We just use bank name for now to simplify.
    public void deposit(double amount, String bankName) {

        // verification placeholder
        boolean ok = bankVerification(amount);

        if (ok) {
            String path = "/account/" + user.getUid();
            DatabaseReference dbRef = db.getReference(path);
            dbRef.child("balance").setValue(acc.getBalance() + amount);

            // write record to user account
            Change c1 = new Change("DEPOSIT", amount, "-1", "Bank", bankName);
            String path1 = "/history/user/" + user.getUid();
            DatabaseReference dbRef1 = db.getReference(path1);
            String key1 = dbRef1.push().getKey();
            c1.setId(key1);
            dbRef1.child(key1).setValue(c1);
        }

    }

    public void withdraw(double amount, String bankName) {

        // verification placeholder
        boolean ok = bankVerification(0);

        if (ok && acc.getBalance() > amount) {
            String path = "/account/" + user.getUid();
            DatabaseReference dbRef = db.getReference(path);
            dbRef.child("balance").setValue(acc.getBalance() - amount);

            // write record to user account
            Change c1 = new Change("WITHDRAW", -amount, "-1", "Bank", bankName);
            String path1 = "/history/user/" + user.getUid();
            DatabaseReference dbRef1 = db.getReference(path1);
            String key1 = dbRef1.push().getKey();
            c1.setId(key1);
            dbRef1.child(key1).setValue(c1);
        }

    }


}
