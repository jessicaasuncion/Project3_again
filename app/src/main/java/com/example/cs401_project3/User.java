package com.example.cs401_project3;

public class User {
    public String firstName, lastName, email, password, c_password;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password, String c_password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.c_password = c_password;
    }
}
