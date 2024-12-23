package com.example.demo.Repository;

import com.example.demo.Models.Course;
import com.example.demo.Models.Enrollment;
import com.example.demo.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {
    Enrollment findByStudentAndCourse(Student student, Course course);
    @Query("SELECT e.course.id FROM Enrollment e WHERE e.student.id = :studentId")
    List<Long> findCourseIdsByStudentId(@Param("studentId") Long studentId);
}
