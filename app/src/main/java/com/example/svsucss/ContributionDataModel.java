package com.example.svsucss;

public class ContributionDataModel {

    String addersid;
    String date;
    String place;
    String sponsi;
    Long packed_food;
    Double dry_ration;


    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Long getHoursDevoted() {
        return hoursDevoted;
    }

    public void setHoursDevoted(Long hoursDevoted) {
        this.hoursDevoted = hoursDevoted;
    }

    Double cash;
    Long hoursDevoted;
    String name;


    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getSponsi() {
        return sponsi;
    }

    public Long getPacked_food() {
        return packed_food;
    }

    public double getDry_ration() {
        return dry_ration;
    }

    public String getAddersid() {
        return addersid;
    }

    public void setAddersid(String addersid) {
        this.addersid = addersid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setSponsi(String sponsi) {
        this.sponsi = sponsi;
    }

    public void setPacked_food(Long packed_food) {
        this.packed_food = packed_food;
    }

    public void setDry_ration(Double dry_ration) {
        this.dry_ration = dry_ration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
