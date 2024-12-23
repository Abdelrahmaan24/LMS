package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServicesTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private EnrollmentRepo enrollmentRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private StudentServices studentServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");

        when(studentRepo.save(student)).thenReturn(student);

        Student result = studentServices.createStudent(student);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        verify(studentRepo, times(1)).save(student);
    }

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentServices.getStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(studentRepo, times(1)).findById(1L);
    }

    @Test
    void testUpdateStudent() {
        Student existingStudent = new Student();
        existingStudent.setId(1L);
        existingStudent.setName("John");

        Student updatedStudent = new Student();
        updatedStudent.setName("John Updated");

        when(studentRepo.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepo.save(existingStudent)).thenReturn(existingStudent);

        Student result = studentServices.updateStudent(1L, updatedStudent);

        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        verify(studentRepo, times(1)).save(existingStudent);
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setId(1L);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        studentServices.deleteStudent(1L);

        verify(studentRepo, times(1)).delete(student);
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());

        when(studentRepo.findAll()).thenReturn(students);

        List<Student> result = studentServices.getAllStudents();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepo, times(1)).findAll();
    }

    @Test
    void testGetAllCoursesByStudentId() {
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        Course course = new Course();
        enrollment.setCourse(course);
        student.setEnrollments(List.of(enrollment));

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        List<Course> result = studentServices.getAllCoursesByStudentId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(course, result.get(0));
    }

    @Test
    void testGetCoursesNotEnrolledByStudent() {
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        Course enrolledCourse = new Course();
        Course notEnrolledCourse = new Course();

        enrollment.setCourse(enrolledCourse);
        student.setEnrollments(List.of(enrollment));

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepo.findAll()).thenReturn(List.of(enrolledCourse, notEnrolledCourse));

        List<Course> result = studentServices.getCoursesNotEnrolledByStudent(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(notEnrolledCourse, result.get(0));
    }

    @Test
    void testSubmitAssignment() {
        Student student = new Student();
        student.setId(1L);
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        Submission submission = new Submission();

        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(submissionRepository.save(submission)).thenReturn(submission);

        Submission result = studentServices.submitAssignment(1L, 1L, submission);

        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(assignment, result.getAssignment());
        verify(submissionRepository, times(1)).save(submission);
    }
}
