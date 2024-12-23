package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AssignmentDto {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Long courseId;
}
