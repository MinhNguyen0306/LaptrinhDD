package com.example.thtuan3_nguyenleminh.Model;

public class OrderItem {

    private int id;
    private int quantity;
    private double totalprice;
    private Food food;
    private Order order;

    public OrderItem(){}

    public OrderItem(Food food){
        this.food = food;
    }

    public OrderItem(int quantity, double totalprice, Food food) {
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.food = food;
        this.order = order;
    }

    public OrderItem(int quantity, double totalprice, Food food, Order order) {
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.food = food;
        this.order = order;
    }

    public OrderItem(int id, int quantity, double totalprice, Food food, Order order) {
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.food = food;
        this.order = order;
    }

    public OrderItem(int id, int quantity, double totalprice, Food food) {
        this.id = id;
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
