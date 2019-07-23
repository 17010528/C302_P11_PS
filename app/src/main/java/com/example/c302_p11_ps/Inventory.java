package com.example.c302_p11_ps;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Inventory implements Serializable {

    private String id;
    private String name;
    private double cost;

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // needed by firebase

    public Inventory(){

    }

    public Inventory(double cost , String name){
        this.cost = cost;
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
