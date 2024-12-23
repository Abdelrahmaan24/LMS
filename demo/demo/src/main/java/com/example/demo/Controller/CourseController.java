package com.example.demo.Controller;

import com.example.demo.Dto.LessonDto;
import com.example.demo.Models.Course;
import com.example.demo.Models.Lesson;
import com.example.demo.Services.CourseServices;
import com.example.demo.Services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/Course")
public class CourseController {

    @Autowired
    private CourseServices courseServices;
    @Autowired
    private LessonServices lessonServices;


    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseServices.createCourse(course));
    }


    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseServices.getCourseById(id));
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseServices.getAllCourses());
    }

    // add lessson to a course
    @PostMapping("/{id}/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(@PathVariable Long id, @RequestBody Lesson lesson) {
        Lesson addedLesson = courseServices.addLessonToCourse(id, lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedLesson);
    }


    // Create a new lesson
    @PostMapping(value = "/{id}/Createlesson")
    public ResponseEntity<Lesson> createLesson(@PathVariable long id, @RequestBody LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setOtp(lessonDto.getOtp());
        lesson.setCourse(courseServices.getCourseById(id));
        lesson.setOtpStartTime(lessonDto.getOtpStartTime());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonServices.createLesson(lesson));
    }
}
