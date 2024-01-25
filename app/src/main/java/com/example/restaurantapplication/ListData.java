package com.example.restaurantapplication;

public class ListData {
    String name, location;
    int ingredients, desc;
    int image;
    public ListData(String name, String city, int ingredients, int desc, int image) {
        this.name = name;
        this.location = city;
        this.ingredients = ingredients;
        this.desc = desc;
        this.image = image;
    }
}
