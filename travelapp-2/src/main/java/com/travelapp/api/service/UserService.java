package com.travelapp.api.service;

import com.travelapp.api.entity.User;
import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
	boolean authenticateUser(String email, String password);
	User getUserByEmail(String email);
}