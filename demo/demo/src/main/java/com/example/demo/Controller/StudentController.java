package com.example.demo.Controller;

import com.example.demo.Dto.StudentDto;
import com.example.demo.Models.*;
import com.example.demo.Services.AssignmentService;
import com.example.demo.Services.EnrollmentServices;
import com.example.demo.Services.StudentServices;
import com.example.demo.Dto.SubmissionDto;
import com.example.demo.Services.UserServices;
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
    private AssignmentService assignmentService;
    @Autowired
    private UserServices userServices;

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
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        Student updatedStudent = new Student();
        updatedStudent.setName(studentDto.getName());
        updatedStudent.setEmail(studentDto.getEmail());
        updatedStudent.setPassword(studentDto.getPassword());
        updatedStudent.setRole(studentDto.getRole());
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
    }

    @PostMapping("/{studentId}/assignments/{assignmentId}/submit")
    public ResponseEntity<Submission> submitAssignment(
            @PathVariable Long studentId,
            @PathVariable Long assignmentId,
            @RequestBody SubmissionDto submissionDto) {

        // Assuming you have methods in your service layer to get the assignment and student by ID.
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        User student = userServices.getUserById(studentId); // assuming Student extends User, or replace accordingly

        // Create Submission object and set the data from DTO
        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setSubmissionDate(submissionDto.getSubmissionDate());
        submission.setFileUrl(submissionDto.getFileUrl());
        submission.setGrade(submissionDto.getGrade());

        // Save the submission using the service layer
        Submission createdSubmission = studentService.submitAssignment(studentId, assignmentId, submission);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
    }


}
