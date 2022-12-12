package com.latutslab_00000053580.foodro;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String name;
    private int price;
    private String image;
    private int merchant_id;
    private int listed;

    public Food(int id, String name, int price, String image, int merchant_id, int listed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.merchant_id = merchant_id;
        this.listed = listed;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getListed() {
        return listed;
    }

    public void setListed(int listed) {
        this.listed = listed;
    }
}
