package com.travelapp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.travelapp.api.entity.User;
import com.travelapp.api.service.UserService;
import com.travelapp.api.request.UserRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setFullname(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setPassword(userRequest.getPassword());
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword(); 

        // Assume authenticateUser method returns true if login is successful
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated) {
            // Tạo một đối tượng JSON chứa thông điệp phản hồi
            String message = "Login successful";
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(message);

            // Trả về mã 200 OK và dữ liệu JSON trong ResponseEntity
            return ResponseEntity.ok(jsonResponse);
        } else {
            // Tạo một đối tượng JSON chứa thông điệp phản hồi
            String errorMessage = "Invalid email or password";
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(errorMessage);

            // Trả về mã 401 Unauthorized và dữ liệu JSON trong ResponseEntity
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}