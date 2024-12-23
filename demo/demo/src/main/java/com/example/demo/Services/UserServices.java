package com.example.demo.Services;

import com.example.demo.Models.Admin;
import com.example.demo.Models.Instructor;
import com.example.demo.Models.Student;
import com.example.demo.Models.User;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.InstructorRepo;
import com.example.demo.Repository.StudentRepo;
import com.example.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServices {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private InstructorRepo instructorRepository;

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private JwtServices jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Map<String, Object> registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        User savedUser;

        // Add user to the appropriate role table
        switch (user.getRole()) {
            case Student:
                Student student = new Student();
                student.setId(newUser.getId());
                student.setName(newUser.getName());
                student.setEmail(newUser.getEmail());
                student.setPassword(newUser.getPassword());
                student.setRole(newUser.getRole());
                savedUser = studentRepository.save(student);
                break;
            case Instructor:
                Instructor instructor = new Instructor();
                instructor.setId(newUser.getId());
                instructor.setName(newUser.getName());
                instructor.setEmail(newUser.getEmail());
                instructor.setPassword(newUser.getPassword());
                instructor.setRole(newUser.getRole());
                savedUser = instructorRepository.save(instructor);
                break;
            case Admin:
                Admin admin = new Admin();
                admin.setId(newUser.getId());
                admin.setName(newUser.getName());
                admin.setEmail(newUser.getEmail());
                admin.setPassword(newUser.getPassword());
                admin.setRole(newUser.getRole());
                savedUser = adminRepository.save(admin);
                break;
            default:
                throw new IllegalArgumentException("Invalid role.");
        }
        String token = jwtService.generateToken(savedUser);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", savedUser);
        return response;
    }

    public Map<String, Object> loginUser(User user) {
        User foundUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        String token = jwtService.generateToken(foundUser);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", foundUser);
        return response;
    }


    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get a user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }

    // Update a user
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }

    // Delete a user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}