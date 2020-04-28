package com.example.cs401_project3;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    public String firstName, lastName, birthday, major;

    public UserInfo() {

    }

    public UserInfo(String firstName, String lastName, String birthday, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.major = major;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("birthday", birthday);
        result.put("major", major);

        return result;
    }
}
