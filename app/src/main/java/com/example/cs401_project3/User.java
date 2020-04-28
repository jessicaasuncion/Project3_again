package com.example.cs401_project3;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String email;

    // default constructor
    public User() {

    }

    // constructor for MainActivity
    public User(String email) {
        this.email = email;
    }
}
