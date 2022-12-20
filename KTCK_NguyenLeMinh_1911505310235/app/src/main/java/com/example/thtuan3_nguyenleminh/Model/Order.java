package com.example.thtuan3_nguyenleminh.Model;

import java.util.Date;
import java.util.Set;

public class Order {
    private int id;
    private int totalItems;
    private double totalPrices;
    private int status;
    private String fromAddress;
    private String created_at;
    private User user;
    private String foodsName;

    private Set<OrderItem> orderItems;

    public Order(){}

    public Order(int totalItems, double totalPrices, int status, String fromAddress, String created_at, String foodsName) {
        this.totalItems = totalItems;
        this.totalPrices = totalPrices;
        this.status = status;
        this.fromAddress = fromAddress;
        this.created_at = created_at;
        this.user = user;
        this.foodsName = foodsName;
    }

    public Order(int totalItems, double totalPrices, int status, String fromAddress) {
        this.totalItems = totalItems;
        this.totalPrices = totalPrices;
        this.status = status;
        this.fromAddress = fromAddress;
        this.created_at = created_at;
        this.user = user;
    }

    public Order(int totalItems, double totalPrices, int orderstatus, String fromAddress, String created_at, User user, Set<OrderItem> orderItems) {
        this.totalItems = totalItems;
        this.totalPrices = totalPrices;
        this.status = orderstatus;
        this.fromAddress = fromAddress;
        this.created_at = created_at;
        this.user = user;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public double getTotalPrices() {
        return totalPrices;
    }

    public int getStatus() {
        return status;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalPrices(double totalPrices) {
        this.totalPrices = totalPrices;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFoodsName() {
        return foodsName;
    }

    public void setFoodsName(String foodsName) {
        this.foodsName = foodsName;
    }
}
