package com.example.demo.Repository;

import com.example.demo.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Custom query to find questions by quiz ID
    List<Question> findByQuizId(Long quizId);
}
