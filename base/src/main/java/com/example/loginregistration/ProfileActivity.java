package com.example.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity{
    private TextView  fnameTextView,lnameTextView;
    private TextView emailTextView, passwordTextView;
    private EditText addressEditText, cardnumEditText, expEditText;
    private ImageView userImageView, emailImageView, addressImageView;
    private ImageView passwordImageView, expImageView,cardnumImageView;
    private String email,fname,lname,password;
    private DatabaseReference mDatabase;
    private Button saveButton,backButton;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseUser user;
    private FirebaseDatabase db;
    private String uid;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        fnameTextView = findViewById(R.id.first_textView);
        lnameTextView = findViewById(R.id.last_textView);
        emailTextView = findViewById(R.id.email_textview);
        passwordTextView = findViewById(R.id.password_textview);
        addressEditText = findViewById(R.id.address_edittext);
        cardnumEditText = findViewById(R.id.cardnum_edittext);
        expEditText = findViewById(R.id.exp_edittext);
        userImageView = findViewById(R.id.user_imageview);
        emailImageView = findViewById(R.id.email_imageview);
        passwordImageView = findViewById(R.id.password_imageview);
        addressImageView = findViewById(R.id.address_imageview);
        cardnumImageView = findViewById(R.id.cardnum_imageview);
        expImageView = findViewById(R.id.exp_imageview);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(uid);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                email = user.getEmail();
                fname = dataSnapshot.child("first").getValue().toString();
                lname = dataSnapshot.child("last").getValue().toString();
                password = dataSnapshot.child("password").getValue().toString();

                fnameTextView.setText(fname);
                lnameTextView.setText(lname);
                emailTextView.setText(email);
                passwordTextView.setText(password);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read values", databaseError.toException());
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                    addInfo();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,Debug.class));
            }

        });

    }

    private void addInfo(){
        String addy = addressEditText.getText().toString().trim(); // address
        String cnum = cardnumEditText.getText().toString().trim(); // card number
        String exp = expEditText.getText().toString().trim(); // expiration date

        if(!TextUtils.isEmpty(addy)) {

            String path = "/user/" + uid;
            DatabaseReference dbref = db.getReference(path);
            dbref.child("address").setValue(addy);
            Toast.makeText(this, "Address added", Toast.LENGTH_LONG).show();
        }
        else {

        }

        if(!TextUtils.isEmpty(cnum)) {

            String path = "/user/" + uid;
            DatabaseReference dbref = db.getReference(path);
            dbref.child("card num").setValue(cnum);
            Toast.makeText(this,"Card number added!", Toast.LENGTH_LONG).show();
        }
        else {

        }

        if(!TextUtils.isEmpty(exp)) {

            String path = "/user/" + uid;
            DatabaseReference dbref = db.getReference(path);
            dbref.child("expiration date").setValue(exp);
            Toast.makeText(this,"Expiration date added!", Toast.LENGTH_LONG).show();
        }
        else {

        }
    }

}

