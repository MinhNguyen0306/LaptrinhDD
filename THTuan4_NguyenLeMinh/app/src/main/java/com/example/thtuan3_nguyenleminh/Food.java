package com.example.thtuan3_nguyenleminh;

import java.io.Serializable;

public class Food implements Serializable {
    private int image, numItem;
    private double gia;
    private String name;

    public Food(int image, int numItem, double gia, String name) {
        this.image = image;
        this.numItem = numItem;
        this.gia = gia;
        this.name = name;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

