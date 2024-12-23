package com.example.demo.Services;

import com.example.demo.Models.Attendance;
import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Repository.AttendanceRepo;
import com.example.demo.Repository.LessonRepo;
import com.example.demo.Repository.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LessonServicesTest {

    @Mock
    private LessonRepo lessonRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private AttendanceRepo attendanceRepo;

    @InjectMocks
    private LessonServices lessonServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // Attendance Tests
    @Test
    void testGenerateOtp() {
        // Arrange
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        when(lessonRepo.findById(1L)).thenReturn(Optional.of(lesson));

        // Act
        lessonServices.generateOtp(1L);

        // Assert
        assertNotNull(lesson.getOtp());
        verify(lessonRepo, times(1)).save(lesson);
    }

    @Test
    void testGenerateOtp_LessonNotFound() {
        // Arrange
        when(lessonRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lessonServices.generateOtp(1L));
        assertEquals("Lesson not found with ID 1", exception.getMessage());
    }

    @Test
    void testMarkAttendance(){
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setOtp("8734");
        lesson.setOtpStartTime(LocalDateTime.now());

        Student student = new Student();
        student.setId(1L);

        when(lessonRepo.findById(1L)).thenReturn(Optional.of(lesson));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> lessonServices.markAttendance(1L,1L,"8734"));
        verify(attendanceRepo,times(1)).save(any(Attendance.class));
    }

    @Test
    void testMarkAttendance_LessonNotFound() {
        when(lessonRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> lessonServices.markAttendance(1L,1L,"8734"));
        assertEquals("Lesson not found with ID 1", exception.getMessage());
        verify(attendanceRepo,never()).save(any(Attendance.class));
    }

    @Test
    void testMarkAttendance_StudentNotFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lessonServices.markAttendance(1L,1L,"8734"));
        assertEquals("Lesson not found with ID 1", exception.getMessage());
        verify(attendanceRepo,never()).save(any(Attendance.class));
    }

    @Test
    void testMarkAttendance_InvalidOTP() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setOtp("8734");
        lesson.setOtpStartTime(LocalDateTime.now());

        Student student = new Student();
        student.setId(1L);

        when(lessonRepo.findById(1L)).thenReturn(Optional.of(lesson));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        RuntimeException exception = assertThrows(
                RuntimeException.class, () ->
                lessonServices.markAttendance(1L,1L,"1256")); // Different OTP: 1256 != 8734
        assertEquals("OTP does not match", exception.getMessage());
        verify(attendanceRepo,never()).save(any(Attendance.class));
    }

    @Test
    void testMarkAttendance_ExpiredOTP() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setOtp("8734");
        lesson.setOtpStartTime(LocalDateTime.now().minusMinutes(16)); // The valid duration of otp is only 15 minutes.

        Student student = new Student();
        student.setId(1L);

        when(lessonRepo.findById(1L)).thenReturn(Optional.of(lesson));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        RuntimeException exception = assertThrows(
                RuntimeException.class, () ->
                        lessonServices.markAttendance(1L,1L,"8734"));
        assertEquals("OTP has expired", exception.getMessage());
        verify(attendanceRepo,never()).save(any(Attendance.class));
    }
}

