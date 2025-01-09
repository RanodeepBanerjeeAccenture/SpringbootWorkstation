package com.accenture.usermanagementapp.service;

import com.accenture.usermanagementapp.entity.User;
import com.accenture.usermanagementapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public User registerUser(String email, String password, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);  // No password encoding
        user.setRole(User.Role.valueOf(role));  // Assign role
        return userRepository.save(user);
    }

    // Find a user by their email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);  // This will save the user
    }

    // Add the deleteById method
    public void delete(User user) {
        userRepository.delete(user); // Calls deleteById from JpaRepository
    }

    // Find a user by their ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Get all users (for admin)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Update a user's information
    public User updateUser(Long id, String email, String password, String role) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setEmail(email);
            existingUser.setPassword(password);  // Update password as is
            existingUser.setRole(User.Role.valueOf(role));
            return userRepository.save(existingUser);
        }
        return null;  // If user not found
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException e) {
            // Log the data access exception and handle the error appropriately
            log.error("Error deleting user with ID: {}", id, e);
        }
    }

    // Change a user's password (no hashing)
    public User changePassword(Long id, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);  // Directly update password
            return userRepository.save(user);
        }
        return null;  // If user not found
    }
}
