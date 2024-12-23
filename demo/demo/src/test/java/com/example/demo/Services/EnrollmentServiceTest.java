package com.example.demo.Services;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Student;
import com.example.demo.Repository.CourseRepo;
import com.example.demo.Repository.EnrollmentRepo;
import com.example.demo.Repository.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepo enrollmentRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private EnrollmentServices enrollmentServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnrollStudentInCourse_Success() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);
        student.setName("Test Student");

        Course course = new Course();
        course.setId(courseId);
        course.setTitle("Test Course");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("active");

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollmentRepo.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentServices.enrollStudentInCourse(studentId, courseId);

        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(course, result.getCourse());
        assertEquals("active", result.getStatus());
        verify(studentRepo, times(1)).findById(studentId);
        verify(courseRepo, times(1)).findById(courseId);
        verify(enrollmentRepo, times(1)).save(any(Enrollment.class));
    }

    @Test
    void testEnrollStudentInCourse_ThrowsExceptionWhenStudentNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepo.findById(studentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> enrollmentServices.enrollStudentInCourse(studentId, courseId));

        assertEquals("Student with ID 1 not found.", exception.getMessage());
        verify(studentRepo, times(1)).findById(studentId);
        verify(courseRepo, never()).findById(anyLong());
        verify(enrollmentRepo, never()).save(any(Enrollment.class));
    }

    @Test
    void testEnrollStudentInCourse_ThrowsExceptionWhenCourseNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepo.findById(courseId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> enrollmentServices.enrollStudentInCourse(studentId, courseId));

        assertEquals("Course with ID 1 not found.", exception.getMessage());
        verify(studentRepo, times(1)).findById(studentId);
        verify(courseRepo, times(1)).findById(courseId);
        verify(enrollmentRepo, never()).save(any(Enrollment.class));
    }
}
