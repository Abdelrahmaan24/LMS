package com.example.demo.Controller;

import com.example.demo.Dto.InstructorDto;
import com.example.demo.Dto.StudentDto;
import com.example.demo.Models.*;
import com.example.demo.Services.AdminServices;
import com.example.demo.Services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/Admin")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private CourseServices courseServices;


    // Create an admin
    @PostMapping(value = "/CreateAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createdAdmin = adminServices.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }


    // Create Student
    @PostMapping(value = "/CreateStudent", consumes = "application/json")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setPassword(studentDto.getPassword());
        student.setRole(studentDto.getRole());

        Student createdStudent = adminServices.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    // Create Instructor
    @PostMapping(value = "/CreateInstructor", consumes = "application/json")
    public ResponseEntity<Instructor> createInstructor(@RequestBody InstructorDto instructorDto) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorDto.getName());
        instructor.setEmail(instructorDto.getEmail());
        instructor.setPassword(instructorDto.getPassword());
        instructor.setRole(instructorDto.getRole());

        Instructor createdInstructor = adminServices.createInstructor(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstructor);

    }


    // Get all admins
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminServices.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // Get an admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminServices.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    // Update an admin
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails) {
        Admin updatedAdmin = adminServices.updateAdmin(id, adminDetails);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Delete an admin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminServices.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }


    // Update Course
    @PutMapping(value = "/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        Course course = adminServices.updateCourse(courseId, updatedCourse);
        return ResponseEntity.ok(course);
    }


    // delete Course
    @DeleteMapping(value = "/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        adminServices.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    // get all student enrolled in course
    @GetMapping(value = "/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<Student> students = courseServices.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }

}
