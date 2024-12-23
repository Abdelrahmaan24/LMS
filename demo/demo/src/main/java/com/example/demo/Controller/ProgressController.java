package com.example.demo.Controller;


import com.example.demo.Models.Attendance;
import com.example.demo.Models.Assignment;
import com.example.demo.Models.Quiz;
import com.example.demo.Services.AttendanceService;
import com.example.demo.Services.AssignmentService;
import com.example.demo.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/student/{studentId}/quizzes")
    public ResponseEntity<List<Quiz>> getQuizzesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(quizService.getQuizzesByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignmentsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByStudentId(studentId));
    }

    @GetMapping("/student/{studentId}/attendance")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudentId(studentId));
    }
}
