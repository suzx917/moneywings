package com.example.loginregistration;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    // used to keep track of business buttons
    ArrayList<Button> mButtonList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feed");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        // list of business names
        List<String> businesses = new ArrayList();
        int numbus = 5;
        for (int i = 0; i < numbus; i++){
            businesses.add("Business " + i);
        }



        LinearLayout layout = (LinearLayout) findViewById(R.id.rootLayout);

        // dynamically creating buttons
        for (int i = 0; i < businesses.size(); i++) {
            Button btn = new Button(this);

            btn.setText(businesses.get(i)); // use list of business names for buttons
            btn.setId(1000 + i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(Button button :mButtonList) {
                        // code here applies to every button *universally*

                        Toast toast = Toast.makeText(getApplicationContext(), "Liked!", Toast.LENGTH_SHORT);
                        toast.show();

                        if (button.getId() == v.getId()) {
                            // code here applies to each business button *separately*
                            button.setEnabled(false);
                        }
                    }
                }
            });

            layout.addView(btn);
            mButtonList.add(btn);
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
