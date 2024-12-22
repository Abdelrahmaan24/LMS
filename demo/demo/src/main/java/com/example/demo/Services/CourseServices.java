package com.example.demo.Services;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServices {


    @Autowired
    private CourseRepo courseRepo;

    public Course createCourse(Course course) {
        if (courseRepo.existsByTitle(course.getTitle())) {
            throw new RuntimeException("Course with title '" + course.getTitle() + "' already exists.");
        }
        return courseRepo.save(course);
    }


    public Course getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID " + id));
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }


    public Lesson addLessonToCourse(Long courseId, Lesson lesson) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID " + courseId));

        lesson.setCourse(course); // Link the lesson to the course
        course.addLesson(lesson); // Add the lesson to the course's lesson list

        courseRepo.save(course); // Save the course with the new lesson

        return lesson;
    }


    public List<Student> getStudentsByCourseId(Long courseId) {
            Course course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course with ID " + courseId + " not found."));
            return course.getEnrollments().stream()
                    .map(Enrollment::getStudent)
                    .collect(Collectors.toList());
        }
    }


