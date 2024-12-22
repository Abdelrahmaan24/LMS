package com.example.demo.Dto;

import com.example.demo.Models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudentDto {
    private String name;
    private String email;
    private String password;
    private Role role;
    private List<Long> enrollmentCourseIds; // If enrollments need to be handled during creation (you can modify this as necessary)
}
