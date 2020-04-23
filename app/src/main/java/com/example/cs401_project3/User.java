package com.example.cs401_project3;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    //public String uid, firstName, lastName, email, password, cPassword;
    public String email;

    // default constructor
    public User() {

    }

    // constructor
    public User(String email) {
        this.email = email;
    }

/*
    // constructor
    public User(String uid, String firstName, String lastName, String email, String password, String cPassword) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cPassword = cPassword;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("password", password);
        result.put("cPassword", cPassword);

        return result;
    }*/
}
