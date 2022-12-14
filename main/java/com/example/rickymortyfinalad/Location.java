package com.example.rickymortyfinalad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Location {
    private int id;
    private String name;
    private String type;
    private String dimension;

    public Location(int id, String name, String type, String dimension){
        this.id = id;
        this.name = name;
        this.type = type;
        this.dimension = dimension;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}