package com.example.demo.Repository;

import com.example.demo.Models.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    // Custom query to find options by question ID
    List<Option> findByQuestionId(Long questionId);
}