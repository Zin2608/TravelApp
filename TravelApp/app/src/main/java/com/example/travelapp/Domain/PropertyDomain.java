package com.example.travelapp.Domain;

import java.io.Serializable;

public class PropertyDomain implements Serializable {
    private String title;
    private String type;
    private String address;
    private String description;
    private String picPath;
    private int price;
    private int bed;
    private int bath;
    private int area;
    private boolean garage;
    private double score;
    private boolean isFav;

    public PropertyDomain(String title, String type, String address, String description, String picPath, int price, int bed, int bath, int area, boolean garage, double score, boolean isFav) {
        this.title = title;
        this.type = type;
        this.address = address;
        this.description = description;
        this.picPath = picPath;
        this.price = price;
        this.bed = bed;
        this.bath = bath;
        this.area = area;
        this.garage = garage;
        this.score = score;
        this.isFav = isFav;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getBath() {
        return bath;
    }

    public void setBath(int bath) {
        this.bath = bath;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int size) {
        this.area = size;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public double getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean favorite) {
        isFav = favorite;
    }
}
