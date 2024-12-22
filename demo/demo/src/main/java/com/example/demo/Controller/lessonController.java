package com.example.demo.Controller;

import com.example.demo.Models.Lesson;
import com.example.demo.Services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/lesson")
public class lessonController {

    @Autowired
    private LessonServices lessonServices;


    // Create a new lesson
    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonServices.createLesson(lesson));
    }

    // Update a lesson
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        return ResponseEntity.ok(lessonServices.updateLesson(id, lesson));
    }

    // Delete a lesson
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonServices.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    // Get all lessons
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonServices.getAllLessons());
    }

    // Get a lesson by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonServices.getLessonById(id));
    }
}
