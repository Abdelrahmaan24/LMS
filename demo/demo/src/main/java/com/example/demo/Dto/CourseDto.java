package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CourseDto {
    private String title;
    private String description;
    private String duration;
    private List<String> mediaFiles;
    private Long instructorId; // To map the course to an instructor
}
