package com.example.loginregistration.businessObj;

import android.widget.ImageView;

public class business {
    private String id;
    private double value;

    private String Name;
    private String Country;
    private String Description;

    public business() {

    }

    public business(String Name, String Country, String Description, ImageView Image) {
        this.Name = Name;
        this.Country = Country;
        this.Description = Description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}