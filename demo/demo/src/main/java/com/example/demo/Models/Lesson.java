package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "Lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // For attendance
    private String otp;
    private LocalDateTime otpStartTime;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<Attendance> attendanceList = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpStartTime() {
        return otpStartTime;
    }

    public void setOtpStartTime(LocalDateTime otpStartTime) {
        this.otpStartTime = otpStartTime;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
}

