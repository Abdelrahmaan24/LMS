package com.example.demo.Services;

import com.example.demo.Models.Assignment;
import com.example.demo.Models.Course;
import com.example.demo.Models.NotificationType;
import com.example.demo.Models.Student;
import com.example.demo.Repository.AssignmentRepository;
import com.example.demo.Repository.EnrollmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NotificationsService notificationsService; // Add NotificationService

    @Autowired
    private EnrollmentRepo enrollmentRepository;

    @Autowired
    private CourseServices courseServices;

    // Create a new assignment and notify students
    public Assignment createAssignment(Assignment assignment, Long CourseId) {
        // Save the assignment
        Course course = courseServices.getCourseById(CourseId);
        assignment.setCourse(course);
        Assignment savedAssignment = assignmentRepository.save(assignment);

        // Notify students about the new assignment
        List<Student> enrolledStudents = assignment.getCourse().getStudents(); // Assuming a course object is linked to the assignment
        for (Student student : enrolledStudents) {
            notificationsService.createNotification(
                    student,
                    "A new assignment has been created: " + savedAssignment.getTitle(),
                    NotificationType.ASSIGNMENT_CREATED
            );
        }

        return savedAssignment;
    }

    // Get all assignments
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // Get an assignment by ID
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }
    public List<Assignment> getAssignmentsForStudent(Long studentId) {
        // Fetch the course IDs the student is enrolled in
        List<Long> courseIds = enrollmentRepository.findCourseIdsByStudentId(studentId);

        // Fetch assignments for these courses
        return assignmentRepository.findByCourseIdIn(courseIds);
    }

    // Delete an assignment and notify students
    public void deleteAssignment(Long id) {
        // Get the assignment to notify students before deletion
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment != null) {
            List<Student> enrolledStudents = assignment.getCourse().getStudents(); // Assuming course is linked
            for (Student student : enrolledStudents) {
                notificationsService.createNotification(
                        student,
                        "An assignment has been deleted: " + assignment.getTitle(),
                        NotificationType.GENERAL // You can use a more specific type if needed
                );
            }
        }

        // Delete the assignment
        assignmentRepository.deleteById(id);
    }
}
