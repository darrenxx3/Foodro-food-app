package com.latutslab_00000053580.foodro;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private int id;
    private Food food;
    private int status;
    private int quantity;

    public OrderDetail(int id, Food food, int status, int quantity){
        this.id = id;
        this.food = food;
        this.status = status;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Food getFood() {
        return food;
    }

    public int getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int calculateTotalPayment(){
        return quantity * this.getFood().getPrice();
    }
}


