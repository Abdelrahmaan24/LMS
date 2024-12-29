package com.example.demo.Controller;

import com.example.demo.Dto.LessonDto;
import com.example.demo.Models.Lesson;
import com.example.demo.Services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/lesson")
public class lessonController {

    @Autowired
    private LessonServices lessonServices;


    // Update a lesson
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setOtp(lessonDto.getOtp());
        lesson.setOtpStartTime(lessonDto.getOtpStartTime());
        return ResponseEntity.ok(lessonServices.updateLesson(id, lesson));
    }

    // Delete a lesson
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonServices.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    // Get all lessons
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonServices.getAllLessons());
    }

    // Get a lesson by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonServices.getLessonById(id));
    }

    @PutMapping(path = "{lessonId}/generateOtp")
    public ResponseEntity<String> generateOtp(@PathVariable Long lessonId) {
        lessonServices.generateOtp(lessonId);
        return ResponseEntity.ok("OTP generated");
    }

    @GetMapping("/{id}/otp")
    public ResponseEntity<String> getOtp(@PathVariable Long id) {
        String otp = lessonServices.getOTP(id);
        return ResponseEntity.ok(otp);
    }
    @PutMapping(path = "{lessonId}/markAttendance")
    public ResponseEntity<String> markAttendance(
            @PathVariable Long lessonId,
            @RequestParam Long studentId,
            @RequestParam String otp) {
        lessonServices.markAttendance(lessonId,studentId,otp);
        return ResponseEntity.ok("Attendance marked");
    }
}
