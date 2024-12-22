package com.example.demo.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Student extends User {

    private int age;
    private LocalDate dob;

    public Student() {
        super();
    }

    public Student(Long id, String name, String email, String password, Role role, int age, LocalDate dob) {
        super(name, email, password, id, role);
        this.age = age;
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
