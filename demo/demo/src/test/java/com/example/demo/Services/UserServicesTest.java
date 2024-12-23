package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicesTest {

    @Mock
    private UserRepo userRepository;

    @Mock
    private StudentRepo studentRepository;

    @Mock
    private InstructorRepo instructorRepository;

    @Mock
    private AdminRepo adminRepository;

    @Mock
    private JwtServices jwtService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServices userServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_AsStudent() {
        User user = new User();
        user.setEmail("student@example.com");
        user.setName("John Doe");
        user.setPassword("password");
        user.setRole(Role.Student);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setEmail(user.getEmail());
        savedStudent.setName(user.getName());
        savedStudent.setPassword("encodedPassword");
        savedStudent.setRole(Role.Student);

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);
        when(jwtService.generateToken(savedStudent)).thenReturn("jwtToken");

        Map<String, Object> response = userServices.registerUser(user);

        assertNotNull(response);
        assertEquals("jwtToken", response.get("token"));
        assertEquals(savedStudent, response.get("user"));

        verify(studentRepository, times(1)).save(any(Student.class));
        verify(jwtService, times(1)).generateToken(savedStudent);
    }

    @Test
    void testRegisterUser_EmailAlreadyRegistered() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userServices.registerUser(user));
    }

    @Test
    void testLoginUser_Success() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");

        User foundUser = new User();
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword("encodedPassword");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(foundUser));
        when(bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())).thenReturn(true);
        when(jwtService.generateToken(foundUser)).thenReturn("jwtToken");

        Map<String, Object> response = userServices.loginUser(user);

        assertNotNull(response);
        assertEquals("jwtToken", response.get("token"));
        assertEquals(foundUser, response.get("user"));

        verify(jwtService, times(1)).generateToken(foundUser);
    }

    @Test
    void testLoginUser_InvalidEmailOrPassword() {
        User user = new User();
        user.setEmail("invalid@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userServices.loginUser(user));
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("John Doe");

        when(userRepository.save(user)).thenReturn(user);

        User result = userServices.createUser(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userServices.getUserById(1L);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");

        User updatedUser = new User();
        updatedUser.setName("New Name");
        updatedUser.setEmail("new@example.com");
        updatedUser.setPassword("newPassword");
        updatedUser.setRole(Role.Admin);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userServices.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("New Name", existingUser.getName());
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals("newPassword", existingUser.getPassword());
        assertEquals(Role.Admin, existingUser.getRole());

        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userServices.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userServices.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }
}
