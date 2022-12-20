package com.example.thtuan3_nguyenleminh.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private int numItem;
    private double gia;
    private String name;
    private String image;
    private String description;
    private Img imageBitmap;
    private boolean isInCart;

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        this.isInCart = inCart;
    }


    public Img getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Img imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Food(String name) {
        this.name = name;
    }
    public Food(int numItem, double gia, String name) {
        this.numItem = numItem;
        this.gia = gia;
        this.name = name;
    }

    public Food(int numItem, double gia, String name, String image) {
        this.numItem = numItem;
        this.gia = gia;
        this.name = name;
        this.image = image;
    }

    public Food(int id, int numItem, double gia, String name, String image) {
        this.id = id;
        this.numItem = numItem;
        this.gia = gia;
        this.name = name;
        this.image = image;
    }

    public Food(int id, int numItem, double gia, String name, String image, String description) {
        this.id = id;
        this.numItem = numItem;
        this.gia = gia;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Food(int id, int numItem, double gia, String name) {
        this.id = id;
        this.numItem = numItem;
        this.gia = gia;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

