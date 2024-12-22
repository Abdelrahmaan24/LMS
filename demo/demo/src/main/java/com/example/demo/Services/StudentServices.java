package com.example.demo.Services;


import com.example.demo.Models.Student;
import com.example.demo.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentServices {

    @Autowired
    private StudentRepo studentRepo;

    // Create a new student
    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }

    // Get a student by ID
    public Student getStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with ID " + id + " not found."));
    }

    // Update a student
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = getStudentById(id);
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPassword(updatedStudent.getPassword());
        return studentRepo.save(existingStudent);
    }

    // Delete a student
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepo.delete(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
}
