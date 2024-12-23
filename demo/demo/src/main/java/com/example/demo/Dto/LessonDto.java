package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class LessonDto {
    private String title;
    private String otp;
    private LocalDateTime otpStartTime;
    private Long courseId;
}
