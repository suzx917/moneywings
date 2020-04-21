package com.example.loginregistration.fbclass;

public class Account {
    private String id;
    private int type;
    private String name;
    private double balance;

    public Account() {} // empty constructor needed for firebase

    public Account(String i, int t, String n, double b) {
        id = i;
        type = t;
        name = n;
        balance = b;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}

