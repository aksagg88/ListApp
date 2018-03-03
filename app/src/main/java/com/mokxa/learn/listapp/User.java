package com.mokxa.learn.listapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by enterprise on 3/3/18.
 */


public class User {
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ListItemText", username);
        result.put("ListItemCreationDate", email);
        return result;
    }
}
