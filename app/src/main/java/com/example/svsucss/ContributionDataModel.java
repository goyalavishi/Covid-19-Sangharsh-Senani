package com.example.svsucss;

import java.util.Date;

public class ContributionDataModel {

    int id;
    int addersid;
    Date date;
    String place;
    String sponsi;
    int packed_food;
    float dry_ration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getSponsi() {
        return sponsi;
    }

    public int getPacked_food() {
        return packed_food;
    }

    public float getDry_ration() {
        return dry_ration;
    }

    public int getAddersid() {
        return addersid;
    }

    public void setAddersid(int addersid) {
        this.addersid = addersid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setSponsi(String sponsi) {
        this.sponsi = sponsi;
    }

    public void setPacked_food(int packed_food) {
        this.packed_food = packed_food;
    }

    public void setDry_ration(float dry_ration) {
        this.dry_ration = dry_ration;
    }
}
