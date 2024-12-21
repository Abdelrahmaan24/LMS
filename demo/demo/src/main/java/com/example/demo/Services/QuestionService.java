package com.example.demo.Services;


import com.example.demo.Models.Question;
import com.example.demo.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Add a question
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Retrieve questions by quiz ID
    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    // Delete a question by its ID
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
