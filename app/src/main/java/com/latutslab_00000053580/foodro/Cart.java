package com.latutslab_00000053580.foodro;

public class Cart {

    int merchantID;
    int itemID;
    int quantity;
    String name;
    int price;
    String image;

    public Cart(int itemID, int quantity, String name, int price, String image, int merchantID) {
        this.itemID = itemID;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.image = image;
        this.merchantID = merchantID;
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

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotal(){
        return quantity*price;
    }
}
