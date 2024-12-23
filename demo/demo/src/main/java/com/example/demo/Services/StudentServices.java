package com.example.demo.Services;


import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Student;
import com.example.demo.Models.Assignment;
import com.example.demo.Models.Quiz;
import com.example.demo.Models.Submission;
import com.example.demo.Repository.CourseRepo;
import com.example.demo.Repository.StudentRepo;
import com.example.demo.Repository.AssignmentRepository;
import com.example.demo.Repository.SubmissionRepository;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.Repository.EnrollmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServices {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    // Create a new student
    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }

    // Get a student by ID
    public Student getStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with ID " + id + " not found."));
    }

    // Update a student
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = getStudentById(id);
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPassword(updatedStudent.getPassword());
        return studentRepo.save(existingStudent);
    }

    // Delete a student
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepo.delete(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    // Get all courses a student is enrolled in
    public List<Course> getAllCoursesByStudentId(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student with ID " + studentId + " not found."));

        return student.getEnrollments().stream()
                .map(Enrollment::getCourse) // Fetch the course from each enrollment
                .collect(Collectors.toList());
    }


    public List<Course> getCoursesNotEnrolledByStudent(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        List<Course> allCourses = courseRepo.findAll();

        // Get the list of courses the student is enrolled in
        List<Course> enrolledCourses = student.getEnrollments().stream()
                .map(enrollment -> enrollment.getCourse())
                .collect(Collectors.toList());

        // Filter out the courses the student is already enrolled in
        return allCourses.stream()
                .filter(course -> !enrolledCourses.contains(course))
                .collect(Collectors.toList());
    }
    @Autowired
    private EnrollmentRepo enrollmentRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public List<Assignment> getAssignmentsForStudent(Long studentId) {
        List<Long> courseIds = enrollmentRepository.findCourseIdsByStudentId(studentId);
        return assignmentRepository.findByCourseIdIn(courseIds);
    }

    public List<Quiz> getQuizzesForStudent(Long studentId) {
        List<Long> courseIds = enrollmentRepository.findCourseIdsByStudentId(studentId);
        return quizRepository.findByCourseIdIn(courseIds);
    }

    public Submission submitAssignment(Long studentId, Long assignmentId, Submission submission) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        submission.setStudent(student);
        submission.setAssignment(assignment);
        return submissionRepository.save(submission);
    }
}
