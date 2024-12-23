package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesTest {

    @InjectMocks
    private InstructorServices instructorServices;

    @Mock
    private InstructorRepo instructorRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private LessonRepo lessonRepo;

    @Mock
    private EnrollmentRepo enrollmentRepo;

    @Mock
    private StudentRepo studentRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateInstructor() {
        Instructor existingInstructor = new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>());
        Instructor updatedInstructor = new Instructor(1L, "John Smith", "johnsmith@example.com", "newpassword", Role.Instructor, new ArrayList<>());

        when(instructorRepo.findById(1L)).thenReturn(Optional.of(existingInstructor));
        when(instructorRepo.save(existingInstructor)).thenReturn(updatedInstructor);

        Instructor result = instructorServices.updateInstructor(updatedInstructor, 1L);

        assertEquals("John Smith", result.getName());
        assertEquals("johnsmith@example.com", result.getEmail());
        verify(instructorRepo, times(1)).save(existingInstructor);
    }

    @Test
    void testGetAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>()));

        when(instructorRepo.findAll()).thenReturn(instructors);

        List<Instructor> result = instructorServices.getAllInstructors();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testCreateInstructor() {
        Instructor instructor = new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>());
        when(instructorRepo.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.CreateInstructor(instructor);

        assertEquals("John Doe", result.getName());
        verify(instructorRepo, times(1)).save(instructor);
    }

    @Test
    void testGetInstructorById() {
        Instructor instructor = new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>());

        when(instructorRepo.findById(1L)).thenReturn(Optional.of(instructor));

        Instructor result = instructorServices.getInstructorById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testDeleteInstructor() {
        Long instructorId = 1L;

        doNothing().when(instructorRepo).deleteById(instructorId);

        instructorServices.DeleteInstructor(instructorId);

        verify(instructorRepo, times(1)).deleteById(instructorId);
    }

    @Test
    void testCreateCourse() {
        Instructor instructor = new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>());
        Course course = new Course();
        course.setTitle("Math");
        course.setDescription("Basic Math");
        course.setDuration("10 weeks");
        course.setInstructor(instructor);

        when(instructorRepo.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepo.existsByTitle(course.getTitle())).thenReturn(false);
        when(courseRepo.save(course)).thenReturn(course);

        Course result = instructorServices.CreateCourse(1L, course);

        assertEquals("Math", result.getTitle());
        verify(courseRepo, times(1)).save(course);
    }

    @Test
    void testAddLessonToCourse() {
        Instructor instructor = new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>());
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Math");
        course.setInstructor(instructor);

        Lesson lesson = new Lesson();
        lesson.setTitle("Lesson 1");
        lesson.setCourse(course);

        instructor.getCourses().add(course);

        when(instructorRepo.findById(1L)).thenReturn(Optional.of(instructor));
        when(lessonRepo.save(lesson)).thenReturn(lesson);

        Lesson result = instructorServices.addLessonToCourse(1L, 1L, lesson);

        assertEquals("Lesson 1", result.getTitle());
        verify(lessonRepo, times(1)).save(lesson);
    }

    @Test
    void testRemoveStudentFromCourse() {
        Instructor instructor = new Instructor(1L, "John Doe", "john@example.com", "password", Role.Instructor, new ArrayList<>());
        Course course = new Course();
        course.setId(1L);
        course.setInstructor(instructor);

        Student student = new Student();
        student.setId(1L);
        student.setName("Jane Doe");
        student.setEmail("jane@example.com");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(instructorRepo.findById(1L)).thenReturn(Optional.of(instructor));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(enrollmentRepo.findByStudentAndCourse(student, course)).thenReturn(enrollment);

        doNothing().when(enrollmentRepo).delete(enrollment);

        instructorServices.removeStudentFromCourse(1L, 1L, 1L);

        verify(enrollmentRepo, times(1)).delete(enrollment);
    }
}
