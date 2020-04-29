package com.example.loginregistration.fbclass;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Change {
    private String id;
    private String type;
    private double value;

    private String counterPartId;
    private String counterPartType;
    private String counterPartName;
    private String dateTime;

    public Change() {}

    public Change(String id, String type, double value, String counterPartId, String counterPartType, String counterPartName, String dateTime) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.counterPartId = counterPartId;
        this.counterPartType = counterPartType;
        this.counterPartName = counterPartName;
        this.dateTime = dateTime;
    }

    public Change(String type, double val, String cid, String ctype, String cname) {
        this.type = type;
        this.value = val;
        this.counterPartId = cid;
        this.counterPartType = ctype;
        this.counterPartName = cname;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY_HH:mm:ss", Locale.US);
        this.dateTime = sdf.format(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCounterPartId() {
        return counterPartId;
    }

    public void setCounterPartId(String counterPartId) {
        this.counterPartId = counterPartId;
    }

    public String getCounterPartType() {
        return counterPartType;
    }

    public void setCounterPartType(String counterPartType) {
        this.counterPartType = counterPartType;
    }

    public String getCounterPartName() {
        return counterPartName;
    }

    public void setCounterPartName(String counterPartName) {
        this.counterPartName = counterPartName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
