package com.chan.mytest;

import android.graphics.Bitmap;

public class USER {
    private int id;
    private String name;
    private String mobile;
    private String email;
    private String address;
    private Bitmap image;


    public USER(int id, String name, String mobile, String email, String address, Bitmap image) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.image = image;
    }

    public USER(String name, String mobile, String email, String address, Bitmap image) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.image = image;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
