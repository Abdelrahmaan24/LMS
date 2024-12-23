package com.example.demo.Services;

import com.example.demo.Models.Quiz;
import com.example.demo.Repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");

        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        Quiz createdQuiz = quizService.createQuiz(quiz);

        assertNotNull(createdQuiz);
        assertEquals("Sample Quiz", createdQuiz.getTitle());

        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    void testGetQuizById() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Sample Quiz");

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Quiz foundQuiz = quizService.getQuizById(1L);

        assertNotNull(foundQuiz);
        assertEquals("Sample Quiz", foundQuiz.getTitle());

        verify(quizRepository, times(1)).findById(1L);
    }
}
