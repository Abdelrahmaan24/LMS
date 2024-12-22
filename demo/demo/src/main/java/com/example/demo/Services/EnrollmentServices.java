package com.example.demo.Services;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Student;
import com.example.demo.Repository.CourseRepo;
import com.example.demo.Repository.EnrollmentRepo;
import com.example.demo.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServices {

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;


    // Enroll a student in a course
    public Enrollment enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student with ID " + studentId + " not found."));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course with ID " + courseId + " not found."));

        // Create an enrollment and link the student and course
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
//        student.getEnrollments().add(enrollment);
        enrollment.setCourse(course);
//        course.getEnrollments().add(enrollment);
        enrollment.setStatus("active");

        return enrollmentRepo.save(enrollment);
    }

}
