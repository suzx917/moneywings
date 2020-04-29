package com.example.loginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InvestmentAction extends AppCompatActivity {
    String bid;
    FbUtil fbu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_action);

        final EditText amountEntry = (EditText) findViewById(R.id.amountEntry);

        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        Intent intent = getIntent();
        bid = intent.getStringExtra("bid");
        fbu = new FbUtil();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_entry = amountEntry.getText().toString();



                AlertDialog.Builder builder = new AlertDialog.Builder(InvestmentAction.this);

                try {
                    final int amt = Integer.parseInt(user_entry.trim());
                    amountEntry.setText("");

                    // valid, positive integer
                    // the user is only able to enter numeric digits
                    if ( amt > 0 ) {
                        builder.setMessage("Successfully invested $" + amt)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO: somehow invest the money
                                        int ret = fbu.invest(amt, bid);
                                        if (ret != 0) {
                                            throw new IllegalArgumentException("Invalid Amount");
                                        }
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    // valid integer, but it is less than 1
                    else {
                        builder.setMessage("Please enter a positive amount")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                }
                catch (NumberFormatException e) // just in case something weird happens
                {
                    amountEntry.setText("");

                    builder.setMessage("Error: Invalid Amount")
                            .setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DebugActivity.class));
            }
        });

    }
}
