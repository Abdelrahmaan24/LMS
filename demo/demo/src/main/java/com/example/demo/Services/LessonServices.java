package com.example.demo.Services;

import com.example.demo.Models.Attendance;
import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Repository.AttendanceRepo;
import com.example.demo.Repository.LessonRepo;
import com.example.demo.Repository.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LessonServices {

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AttendanceRepo attendanceRepo;

    public Lesson createLesson(Lesson lesson) {
        return lessonRepo.save(lesson);
    }

    public Lesson updateLesson(Long id, Lesson lessonDetails) {
        Lesson lesson = lessonRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + id));

        lesson.setTitle(lessonDetails.getTitle());
        return lessonRepo.save(lesson);
    }

    public void deleteLesson(Long id) {
        Lesson lesson = lessonRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + id));
        lessonRepo.delete(lesson);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepo.findAll();
    }

    public Lesson getLessonById(Long id) {
        return lessonRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + id));
    }


    // Attendance functions
    @Transactional
    public void generateOtp(Long lessonId) {
        Lesson lesson = lessonRepo.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + lessonId));
        String otp = String.format("%04d",(int) (Math.random()*10000));
        LocalDateTime startTime = LocalDateTime.now();
        lesson.setOtp(otp);
        lesson.setOtpStartTime(startTime);
        lessonRepo.save(lesson);
    }

    @Transactional
    public void markAttendance(Long lessonId, Long studentId, String otp) {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson not found with ID " + lessonId));
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found with ID " + studentId));

        if (!Objects.equals(lesson.getOtp(), otp)){
            throw new RuntimeException("OTP does not match");
        }

        if (Duration.between(lesson.getOtpStartTime(), LocalDateTime.now()).compareTo(Duration.ofMinutes(15)) > 0){
            throw new RuntimeException("OTP has expired");
        }
        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setStudent(student);
        attendance.setAttendanceTime(LocalDateTime.now());
        attendanceRepo.save(attendance);
    }
}
