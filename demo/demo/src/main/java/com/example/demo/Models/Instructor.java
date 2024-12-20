package com.example.demo.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
//@Table(name = "Instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    @OneToMany(mappedBy = "instructor", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses; // Courses taught by the instructor

    public Instructor() {
        super();
    }

    public Instructor(String name, String email, String password, Long id, Role role, List<Course> courses) {
//        super(name, email, password, id, role);
        this.courses = courses;
    }

    // Getters and Setters


    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
