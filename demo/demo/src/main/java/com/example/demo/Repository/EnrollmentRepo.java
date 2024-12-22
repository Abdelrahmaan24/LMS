package com.example.demo.Repository;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {
    Enrollment findByStudentAndCourse(Student student, Course course);
}
