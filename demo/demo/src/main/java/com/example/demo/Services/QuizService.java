package com.example.demo.Services;

import com.example.demo.Models.Course;
import com.example.demo.Models.Quiz;
import com.example.demo.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CourseServices courseServices;

    // Create a new quiz
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Get quiz by ID
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    // Get quizzes by course ID
    public List<Quiz> getQuizzesByCourseId(Long courseId) {
        return quizRepository.findByCourseId(courseId);
    }
}