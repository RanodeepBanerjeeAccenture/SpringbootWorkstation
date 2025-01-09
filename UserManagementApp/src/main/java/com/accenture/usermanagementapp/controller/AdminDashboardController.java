package com.accenture.usermanagementapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.accenture.usermanagementapp.service.UserService;
import com.accenture.usermanagementapp.entity.User;

import java.util.List;
import java.util.Optional;


@Controller
public class AdminDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    private final UserService userService;

    @Autowired
    public AdminDashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adminDashboard")
    public String showAdminDashboard(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "adminDashboard";
    }

    // Get User by ID (for admin)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if user not found
        }
    }

    @PutMapping("/api/admin/users/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            Optional<User> userOptional = userService.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setEmail(userDetails.getEmail());
                user.setPassword(userDetails.getPassword());
                user.setRole(userDetails.getRole());
                User updatedUser = userService.save(user);
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating user with ID: " + id, e);
            return ResponseEntity.status(500).build();
        }
    }


    @DeleteMapping("/api/admin/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build(); // Return 200 OK on successful deletion
        } catch (Exception e) {
            // Log the exception
            logger.error("Error occurred while deleting user with ID " + id, e);
            return ResponseEntity.notFound().build(); // Return 404 Not Found on error
        }
    }

    // Create User
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Email already exists
        }
        User createdUser = userService.registerUser(user.getEmail(), user.getPassword(), user.getRole().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Get All Users (for admin)
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}