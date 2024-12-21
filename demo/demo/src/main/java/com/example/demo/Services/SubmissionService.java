package com.example.demo.Services;

import com.example.demo.Models.Submission;
import com.example.demo.Repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    // Create a new submission
    public Submission createSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    // Get all submissions
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    // Get submissions by assignment ID
    public List<Submission> getSubmissionsByAssignmentId(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    // Get a specific submission by ID
    public Submission getSubmissionById(Long id) {
        return submissionRepository.findById(id).orElse(null); // Use `findById` to get a specific submission
    }

    // Grade a submission
    public Submission gradeSubmission(Long id, Integer grade) {
        Submission submission = submissionRepository.findById(id).orElse(null);
        if (submission != null) {
            submission.setGrade(grade); // Set the grade
            submissionRepository.save(submission); // Save the updated submission
        }
        return submission;
    }

    // Delete a submission
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }
}