package com.example.demo.Controller;


import com.example.demo.Models.Course;
import com.example.demo.Models.Instructor;
import com.example.demo.Models.Lesson;
import com.example.demo.Services.InstructorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/Instructor")
public class InstructorController {

    @Autowired
    private InstructorServices instructorServices;


    // get all Instructors
    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors(){
        return ResponseEntity.ok(instructorServices.getAllInstructors());
    }


    // create Instructors
    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor){
        return ResponseEntity.ok(instructorServices.CreateInstructor(instructor));
    }

    // get Instructors by Id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable("id") Long id) {
        Instructor instructor = instructorServices.getInstructorById(id);
        if (instructor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructor);
    }


    // Update Instructor
    @PutMapping(value = "/{id}")
    public ResponseEntity<Instructor> updateInstructor(@RequestBody Instructor instructor, @PathVariable Long id){
        return ResponseEntity.ok(instructorServices.updateInstructor(instructor, id));
    }


    // Delete Student
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id){
        Instructor instructor = instructorServices.getInstructorById(id);
        if (instructor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructorServices.DeleteInstructor(id));
    }


    // Create course
    @PostMapping(value = "/{id}/CreateCourse")
    public ResponseEntity<Course> createCourse(@PathVariable Long id, @RequestBody Course course) {
        Course createdCourse = instructorServices.CreateCourse(id, course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }


    // Update Course
    @PutMapping(value = "/{instructorId}/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long instructorId, @PathVariable Long courseId, @RequestBody Course updatedCourse) {
        Course course = instructorServices.updateCourse(instructorId, courseId, updatedCourse);
        return ResponseEntity.ok(course);
    }


    // Delete Course
    @DeleteMapping(value = "/{instructorId}/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long instructorId, @PathVariable Long courseId) {
        instructorServices.deleteCourse(instructorId, courseId);
        return ResponseEntity.noContent().build();
    }


    // Add a lesson to a course
    @PostMapping(value = "/{instructorId}/courses/{courseId}/lessons")
    public ResponseEntity<Lesson> addLessonToCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @RequestBody Lesson lesson) {
        Lesson createdLesson = instructorServices.addLessonToCourse(instructorId, courseId, lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
    }
}