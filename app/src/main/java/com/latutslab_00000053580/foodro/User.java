package com.latutslab_00000053580.foodro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int role;
    private int active;
    private String image;

    public User(int id, String firstname, String lastname, String email, int role, int active, String image){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.active = active;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }

    public int getActive() {
        return active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName(){
        return String.format("%s %s", this.firstname, this.lastname);
    }

}
