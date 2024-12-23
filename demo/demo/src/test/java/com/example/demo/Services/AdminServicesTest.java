package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServicesTest {

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private InstructorRepo instructorRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AdminServices adminServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAdmins() {
        List<Admin> admins = Arrays.asList(new Admin(), new Admin());
        when(adminRepo.findAll()).thenReturn(admins);

        List<Admin> result = adminServices.getAllAdmins();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(adminRepo, times(1)).findAll();
    }

    @Test
    void testGetAdminById_Success() {
        Admin admin = new Admin();
        admin.setId(1L);
        when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

        Admin result = adminServices.getAdminById(1L);

        assertNotNull(result);
        assertEquals(admin, result);
        verify(adminRepo, times(1)).findById(1L);
    }

    @Test
    void testGetAdminById_NotFound() {
        when(adminRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminServices.getAdminById(1L));
        assertEquals("Admin not found with ID 1", exception.getMessage());
    }

    @Test
    void testUpdateAdmin_Success() {
        Admin existingAdmin = new Admin();
        existingAdmin.setId(1L);

        Admin updatedAdmin = new Admin();
        updatedAdmin.setName("Updated Name");
        updatedAdmin.setEmail("updated@example.com");
        updatedAdmin.setPassword("newPassword");

        when(adminRepo.findById(1L)).thenReturn(Optional.of(existingAdmin));
        when(adminRepo.save(existingAdmin)).thenReturn(existingAdmin);

        Admin result = adminServices.updateAdmin(1L, updatedAdmin);

        assertNotNull(result);
        assertEquals("Updated Name", existingAdmin.getName());
        assertEquals("updated@example.com", existingAdmin.getEmail());
        assertEquals("newPassword", existingAdmin.getPassword());
        verify(adminRepo, times(1)).save(existingAdmin);
    }

    @Test
    void testDeleteAdmin_Success() {
        Admin admin = new Admin();
        admin.setId(1L);
        when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

        adminServices.deleteAdmin(1L);

        verify(adminRepo, times(1)).delete(admin);
    }

    @Test
    void testCreateAdmin_Success() {
        Admin admin = new Admin();
        admin.setRole(Role.Admin);
        admin.setPassword("password");

        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(adminRepo.save(admin)).thenReturn(admin);

        Admin result = adminServices.createAdmin(admin);

        assertNotNull(result);
        assertEquals("encodedPassword", admin.getPassword());
        verify(adminRepo, times(1)).save(admin);
    }

    @Test
    void testCreateAdmin_InvalidRole() {
        Admin admin = new Admin();
        admin.setRole(Role.Student);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> adminServices.createAdmin(admin));
        assertEquals("Only Admins can be created. Provided role: " + admin.getRole(), exception.getMessage());
    }

    @Test
    void testCreateStudent_Success() {
        Student student = new Student();
        student.setRole(Role.Student);
        student.setPassword("password");

        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(studentRepo.save(student)).thenReturn(student);

        Student result = adminServices.createStudent(student);

        assertNotNull(result);
        assertEquals("encodedPassword", student.getPassword());
        verify(studentRepo, times(1)).save(student);
    }

    @Test
    void testCreateStudent_InvalidRole() {
        Student student = new Student();
        student.setRole(Role.Admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> adminServices.createStudent(student));
        assertEquals("Only students can be created. Provided role: " + student.getRole(), exception.getMessage());
    }

    @Test
    void testCreateInstructor_Success() {
        Instructor instructor = new Instructor();
        instructor.setRole(Role.Instructor);
        instructor.setPassword("password");

        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(instructorRepo.save(instructor)).thenReturn(instructor);

        Instructor result = adminServices.createInstructor(instructor);

        assertNotNull(result);
        assertEquals("encodedPassword", instructor.getPassword());
        verify(instructorRepo, times(1)).save(instructor);
    }

    @Test
    void testCreateInstructor_InvalidRole() {
        Instructor instructor = new Instructor();
        instructor.setRole(Role.Student);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> adminServices.createInstructor(instructor));
        assertEquals("Only instructors can be created. Provided role: " + instructor.getRole(), exception.getMessage());
    }

    @Test
    void testUpdateCourse_Success() {
        Course existingCourse = new Course();
        existingCourse.setId(1L);

        Course updatedCourse = new Course();
        updatedCourse.setTitle("Updated Title");
        updatedCourse.setDescription("Updated Description");
        updatedCourse.setDuration("one week");

        when(courseRepo.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepo.save(existingCourse)).thenReturn(existingCourse);

        Course result = adminServices.updateCourse(1L, updatedCourse);

        assertNotNull(result);
        assertEquals("Updated Title", existingCourse.getTitle());
        assertEquals("Updated Description", existingCourse.getDescription());
        assertEquals("one week", existingCourse.getDuration());
        verify(courseRepo, times(1)).save(existingCourse);
    }

    @Test
    void testUpdateCourse_NotFound() {
        when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminServices.updateCourse(1L, new Course()));
        assertEquals("Course not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteCourse_Success() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));

        adminServices.deleteCourse(1L);

        verify(courseRepo, times(1)).delete(course);
    }

    @Test
    void testDeleteCourse_NotFound() {
        when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adminServices.deleteCourse(1L));
        assertEquals("Course not found with ID: 1", exception.getMessage());
    }
}
