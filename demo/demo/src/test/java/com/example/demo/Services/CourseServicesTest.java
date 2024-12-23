package com.example.demo.Services;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Repository.CourseRepo;
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

class CourseServicesTest {

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private CourseServices courseServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse_Success() {
        Course course = new Course();
        course.setTitle("Test Course");

        when(courseRepo.existsByTitle("Test Course")).thenReturn(false);
        when(courseRepo.save(course)).thenReturn(course);

        Course createdCourse = courseServices.createCourse(course);

        assertNotNull(createdCourse);
        assertEquals("Test Course", createdCourse.getTitle());
        verify(courseRepo, times(1)).existsByTitle("Test Course");
        verify(courseRepo, times(1)).save(course);
    }

    @Test
    void testCreateCourse_ThrowsExceptionWhenTitleExists() {
        Course course = new Course();
        course.setTitle("Existing Course");

        when(courseRepo.existsByTitle("Existing Course")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseServices.createCourse(course));

        assertEquals("Course with title 'Existing Course' already exists.", exception.getMessage());
        verify(courseRepo, times(1)).existsByTitle("Existing Course");
        verify(courseRepo, never()).save(any(Course.class));
    }

    @Test
    void testGetCourseById_Success() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));

        Course foundCourse = courseServices.getCourseById(1L);

        assertNotNull(foundCourse);
        assertEquals(1L, foundCourse.getId());
        verify(courseRepo, times(1)).findById(1L);
    }

    @Test
    void testGetCourseById_ThrowsExceptionWhenNotFound() {
        when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseServices.getCourseById(1L));

        assertEquals("Course not found with ID 1", exception.getMessage());
        verify(courseRepo, times(1)).findById(1L);
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        courses.add(new Course());

        when(courseRepo.findAll()).thenReturn(courses);

        List<Course> allCourses = courseServices.getAllCourses();

        assertNotNull(allCourses);
        assertEquals(2, allCourses.size());
        verify(courseRepo, times(1)).findAll();
    }

    @Test
    void testAddLessonToCourse_Success() {
        Course course = new Course();
        course.setId(1L);
        course.setLessons(new ArrayList<>());

        Lesson lesson = new Lesson();
        lesson.setTitle("Test Lesson");

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepo.save(course)).thenReturn(course);

        Lesson addedLesson = courseServices.addLessonToCourse(1L, lesson);

        assertNotNull(addedLesson);
        assertEquals("Test Lesson", addedLesson.getTitle());
        assertEquals(course, addedLesson.getCourse());
        assertEquals(1, course.getLessons().size());
        verify(courseRepo, times(1)).findById(1L);
        verify(courseRepo, times(1)).save(course);
    }

    @Test
    void testAddLessonToCourse_ThrowsExceptionWhenCourseNotFound() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Test Lesson");

        when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseServices.addLessonToCourse(1L, lesson));

        assertEquals("Course not found with ID 1", exception.getMessage());
        verify(courseRepo, times(1)).findById(1L);
    }

    @Test
    void testGetStudentsByCourseId_Success() {
        Course course = new Course();
        course.setId(1L);

        Enrollment enrollment1 = new Enrollment();
        Student student1 = new Student();
        student1.setId(1L);
        enrollment1.setStudent(student1);

        Enrollment enrollment2 = new Enrollment();
        Student student2 = new Student();
        student2.setId(2L);
        enrollment2.setStudent(student2);

        course.setEnrollments(List.of(enrollment1, enrollment2));

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));

        List<Student> students = courseServices.getStudentsByCourseId(1L);

        assertNotNull(students);
        assertEquals(2, students.size());
        assertEquals(1L, students.get(0).getId());
        assertEquals(2L, students.get(1).getId());
        verify(courseRepo, times(1)).findById(1L);
    }

    @Test
    void testGetStudentsByCourseId_ThrowsExceptionWhenCourseNotFound() {
        when(courseRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseServices.getStudentsByCourseId(1L));

        assertEquals("Course with ID 1 not found.", exception.getMessage());
        verify(courseRepo, times(1)).findById(1L);
    }
}
