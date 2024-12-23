package com.example.demo.Controller;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Student;
import com.example.demo.Services.EnrollmentServices;
import com.example.demo.Services.StudentServices;
import com.example.demo.Models.Quiz;
import com.example.demo.Models.Assignment;
import com.example.demo.Models.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/Student")
public class StudentController {

    @Autowired
    private StudentServices studentService;

    @Autowired
    private EnrollmentServices enrollmentService;


    // Create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    // Get a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    // Update a student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student student = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(student);
    }

    // Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Enroll a student in a course
    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Enrollment> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        Enrollment enrollment = enrollmentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }


    // Get all courses a student is enrolled in
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getAllCoursesByStudentId(@PathVariable Long id) {
        List<Course> courses = studentService.getAllCoursesByStudentId(id);
        return ResponseEntity.ok(courses);
    }


    // get available courses for student
    @GetMapping(value = "/{studentId}/courses/not-enrolled")
    public ResponseEntity<List<Course>> getCoursesNotEnrolledByStudent(@PathVariable Long studentId) {
        List<Course> coursesNotEnrolled = studentService.getCoursesNotEnrolledByStudent(studentId);
        return ResponseEntity.ok(coursesNotEnrolled);
    }
      @GetMapping("/{studentId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignmentsForStudent(@PathVariable Long studentId) {
        List<Assignment> assignments = studentService.getAssignmentsForStudent(studentId);
        return ResponseEntity.ok(assignments);
    }
    @GetMapping("/{studentId}/quizzes")
    public ResponseEntity<List<Quiz>> getQuizzesForStudent(@PathVariable Long studentId) {
        List<Quiz> quizzes = studentService.getQuizzesForStudent(studentId);
        return ResponseEntity.ok(quizzes);
    } @PostMapping("/{studentId}/assignments/{assignmentId}/submit")
    public ResponseEntity<Submission> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestBody Submission submission) {

        Submission createdSubmission = studentService.submitAssignment(studentId, assignmentId, submission);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
    }

}
