package com.collegemanagement.dao;

import com.collegemanagement.model.User;

public class UserDAO {

    public User authenticate(String username, String password) {
        if (username == null || password == null) return null;
        User user = DBConnection.IN_MEMORY_USERS.get(username.trim().toLowerCase());
        if (user != null && user.getPassword().equals(password.trim())) {
            return user;
        }
        return null;
    }
}

