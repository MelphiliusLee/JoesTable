package minhao_CSCI201_Assignment4;

import java.util.List;

public class Restaurant {
    private String name;
    private String address;
    private double rating;
    private List<String> cuisine;
    private int price; 
    private String phoneNumber;

    // Constructor
    public Restaurant(String name, String address, double rating, List<String> cuisine, int price, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.cuisine = cuisine;
        this.price = price;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
