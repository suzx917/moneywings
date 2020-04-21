package com.example.loginregistration.fbclass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Project {
    private String starterUid;
    private double balance;
    private String description;

    public Project(String sUid, String des) {
        starterUid = sUid;
        balance = 0;
        description = des;
    }

    public String getStarterUid() {
        return starterUid;
    }

    public void setStarterUid(String starterUid) {
        this.starterUid = starterUid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
