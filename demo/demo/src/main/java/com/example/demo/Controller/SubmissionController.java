package com.example.demo.Controller;

import com.example.demo.Models.Submission;
import com.example.demo.Services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    // Create a new submission
    @PostMapping
    public ResponseEntity<Submission> createSubmission(@RequestBody Submission submission) {
        Submission createdSubmission = submissionService.createSubmission(submission);
        return ResponseEntity.ok(createdSubmission);
    }

    // Get all submissions for an assignment
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<Submission>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        List<Submission> submissions = submissionService.getSubmissionsByAssignmentId(assignmentId); // Fixed method name
        return ResponseEntity.ok(submissions);
    }

    // Get a specific submission by ID
    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id) {
        Submission submission = submissionService.getSubmissionById(id); // Implement this in the service
        return submission != null ? ResponseEntity.ok(submission) : ResponseEntity.notFound().build();
    }

    // Grade a submission
    @PutMapping("/{id}/grade")
    public ResponseEntity<Submission> gradeSubmission(@PathVariable Long id, @RequestParam Integer grade) {
        Submission gradedSubmission = submissionService.gradeSubmission(id, grade); // Implement this in the service
        return gradedSubmission != null ? ResponseEntity.ok(gradedSubmission) : ResponseEntity.notFound().build();
    }
}
