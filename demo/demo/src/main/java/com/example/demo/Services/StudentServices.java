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

    private final StudentRepo studentRepo;

    @Autowired
    public StudentServices(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> getStudent() {
        return studentRepo.findAll();
    }
}
