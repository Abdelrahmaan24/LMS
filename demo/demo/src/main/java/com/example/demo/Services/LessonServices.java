package com.example.demo.Services;

import com.example.demo.Models.Lesson;
import com.example.demo.Repository.LessonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServices {

    @Autowired
    private LessonRepo lessonRepo;

    public Lesson createLesson(Lesson lesson) {
        return lessonRepo.save(lesson);
    }

    public Lesson updateLesson(Long id, Lesson lessonDetails) {
        Lesson lesson = lessonRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + id));

        lesson.setTitle(lessonDetails.getTitle());
        return lessonRepo.save(lesson);
    }

    public void deleteLesson(Long id) {
        Lesson lesson = lessonRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + id));
        lessonRepo.delete(lesson);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepo.findAll();
    }

    public Lesson getLessonById(Long id) {
        return lessonRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with ID " + id));
    }

}
