package com.example.demo.Dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SubmissionDto {
    private Long id;
    private LocalDateTime submissionDate;
    private String fileUrl;
    private Integer grade;
    private Long assignmentId;
    private Long studentId;
}
