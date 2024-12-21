package com.example.demo.Repository;


import com.example.demo.Models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    // Custom query to find submissions by assignment ID
    List<Submission> findByAssignmentId(Long assignmentId);

    // Custom query to find submissions by student ID
    List<Submission> findByStudentId(Long studentId);
}

