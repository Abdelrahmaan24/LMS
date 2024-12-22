package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    
    @Autowired
    private UserRepo userRepo;

    // Create a new user
    public User createUser(User user) {
        return userRepo.save(user);
    }

    // Get a user by ID
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }

    // Update a user
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        return userRepo.save(existingUser);
    }

    // Delete a user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepo.delete(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
