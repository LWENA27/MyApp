package com.myapp;

public class User {
    private String username;
    private String location;
    private String phone;

    public User(String username, String location, String phone) {
        this.username = username;
        this.location = location;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }
}