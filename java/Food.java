package com.example.webservicerest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Food {
    @Expose
    @SerializedName("id")
    int id;
    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("price")
    int price;
    @Expose
    @SerializedName("image")
    String image;

    public Food() {
    }

    public Food(String name, int price, String image) {
        this.name = name;
        this.price = price;
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
}
