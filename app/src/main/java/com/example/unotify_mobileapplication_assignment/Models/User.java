package com.example.unotify_mobileapplication_assignment.Models;

public class User {
    public String username, email, address, contact;

    public User() {}  // Required for Firebase

    public User(String username, String email, String address, String contact) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.contact = contact;

    }
}
