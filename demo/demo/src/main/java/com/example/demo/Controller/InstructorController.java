package com.example.demo.Controller;

import com.example.demo.Dto.CourseDto;
import com.example.demo.Models.Course;
import com.example.demo.Models.Instructor;
import com.example.demo.Models.Lesson;
import com.example.demo.Models.Student;
import com.example.demo.Services.CourseServices;
import com.example.demo.Services.InstructorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/Instructor")
public class InstructorController {

    @Autowired
    private InstructorServices instructorServices;

    @Autowired
    private CourseServices courseServices;


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
    public ResponseEntity<Course> createCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setMediaFiles(courseDto.getMediaFiles());
        course.setLessons(new ArrayList<Lesson>());
        course.setInstructor(instructorServices.getInstructorById(id));
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



    // get all students enrolled in course
    @GetMapping(value = "/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<Student> students = courseServices.getStudentsByCourseId(courseId);
        return ResponseEntity.ok(students);
    }


    // Remove student from course (with instructor validation)
    @DeleteMapping(value = "/{instructorId}/courses/{courseId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId,
            @PathVariable Long studentId) {

        try {
            instructorServices.removeStudentFromCourse(instructorId, studentId, courseId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Forbidden if the instructor is not authorized
        }
    }
}